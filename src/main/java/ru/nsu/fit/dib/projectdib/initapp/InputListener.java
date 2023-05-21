package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.onBtn;
import static com.almasb.fxgl.dsl.FXGL.onBtnDown;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.onKeyUp;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner.doAction;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.data.Controls;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.entity.components.control.PlayerControlComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.ShootAttackComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.WeaponInventoryComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction.WeaponActionType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;

/**
 * Инициализатор действий со входными данными (например, с клавиатуры)
 */
public class InputListener {

  public boolean skipOther = false;
  private Entity player;

  public InputListener() {
  }

  public boolean isSkipOther() {
    return skipOther;
  }

  public void setSkipOther(boolean skipOther) {
    this.skipOther = skipOther;
  }

  public void initialize(Entity player) {
    this.player = player;
  }

  public void run() {
    //==============================================================================================
    onKey(Controls.UP, "Up", () -> player.getComponent(PlayerControlComponent.class).up());
    onKey(Controls.LEFT, "Left", () -> player.getComponent(PlayerControlComponent.class).left());
    onKey(Controls.DOWN, "Down", () -> player.getComponent(PlayerControlComponent.class).down());
    onKey(Controls.RIGHT, "Right", () -> player.getComponent(PlayerControlComponent.class).right());
    onKey(KeyCode.R, "Unbind", () -> {
      System.out.println("--------------");
      MCClient.getClientState().getIdHashTable().forEach((k, v) -> {
        System.out.println(
            "[" + k + "-" + v.getComponent(DataComponent.class).getId() + "] [" + v.getComponent(
                DataComponent.class).getOwnerID() + "] " + v.getType());
      });
      //Creature hero = player.getComponent(HeroComponent.class).getCreature();
      //hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().xProperty().unbind();
      //hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().yProperty().unbind();
    });
    onBtn(MouseButton.PRIMARY, "shoot", () -> {
      player.getComponent(ShootAttackComponent.class).shoot();
    });
    //player.getComponent(HeroComponent.class).attack());
    getInput().addAction(new UserAction("Use") {
      @Override
      protected void onActionBegin() {
        getGameWorld().getEntitiesByType(EntityType.BUTTON)
            .stream()
            .filter(btn -> btn.hasComponent(CollidableComponent.class) && player.isColliding(btn))
            .forEach(btn -> {
              btn.removeComponent(CollidableComponent.class);
              Entity closedDoor = btn.getObject("closedDoor");
              Entity openedDoor = spawn("openedDoor", closedDoor.getPosition());
              closedDoor.removeFromWorld();
            });
      }
    }, KeyCode.E, VirtualButton.B);
    getInput().addAction(new UserAction("Viewport") {
      @Override
      protected void onActionBegin(){
        if (FXGL.getGameScene().getViewport().getZoom()==0.75){
          FXGL.getGameScene().getViewport().setZoom(0.25);
        }
        else {
          FXGL.getGameScene().getViewport().setZoom(0.75);
        }
        }
      },KeyCode.V,VirtualButton.X);
    getInput().addAction(new UserAction("Take/Throw") {
      @Override
      protected void onActionBegin() {
        FXGL.play(Sounds.pickup_weapon);
        List<Entity> list = player.getComponent(WeaponInventoryComponent.class).findWeapon();
        if (list.size() >= 1) {
          doAction(new Pair<>(MessageType.WEAPON,
              new WeaponAction(WeaponActionType.TAKE,
                  player.getComponent(DataComponent.class).getId(),
                  list.get(0).getComponent(DataComponent.class).getId())));
        } else {
          if (player.getComponent(WeaponInventoryComponent.class).getActiveWeapon() != null) {
            doAction(new Pair<>(MessageType.WEAPON,
                new WeaponAction(WeaponActionType.THROW,
                    player.getComponent(DataComponent.class).getId(),
                    player.getComponent(WeaponInventoryComponent.class).getActiveWeapon()
                        .getComponent(DataComponent.class).getId())));
          }
        }
      }
    }, Controls.TAKE_THROW_WEAPON, VirtualButton.X);
    getInput().

        addAction(new UserAction("Swap weapons") {
          @Override
          protected void onActionBegin() {
            FXGL.play(Sounds.swap_weapon);
            player.getComponent(WeaponInventoryComponent.class).swapWeapon();
          }
        }, Controls.CHANGE_WEAPON, VirtualButton.B);
  }
}