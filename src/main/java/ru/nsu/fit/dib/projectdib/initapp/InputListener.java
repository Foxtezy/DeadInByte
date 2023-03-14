package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.onBtn;
import static com.almasb.fxgl.dsl.FXGL.onBtnDown;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.onKeyUp;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.components.PlayerComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;
import ru.nsu.fit.dib.projectdib.multiplayer.Client;

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
    Client client = new Client();
    //==============================================================================================
    onKey(KeyCode.W, "Up", () -> player.getComponent(PlayerComponent.class).up());
    onKey(KeyCode.A, "Left", () -> player.getComponent(PlayerComponent.class).left());
    onKey(KeyCode.S, "Down", () -> player.getComponent(PlayerComponent.class).down());
    onKey(KeyCode.D, "Right", () -> player.getComponent(PlayerComponent.class).right());
    onKey(KeyCode.R, "Unbind", () -> {
      Creature hero = player.getComponent(PlayerComponent.class).getHero();
      hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().xProperty().unbind();
      hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().yProperty().unbind();
    });
    onBtn(MouseButton.PRIMARY, "shoot", () -> player.getComponent(PlayerComponent.class).attack());
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

    getInput().addAction(new UserAction("Take") {
      @Override
      protected void onActionBegin() {

        Creature hero = player.getComponent(PlayerComponent.class).getHero();
        List<Entity> list = new ArrayList<>(
            getGameWorld().getEntitiesByType(EntityType.WEAPON).stream()
                .filter(weapon -> weapon.hasComponent(CollidableComponent.class)
                    && weapon.isColliding(player)).toList());
        //Удаляем все оружие игрока из списка
        hero.getModule(CreatureWeaponModule.class).getWeaponsList().forEach(weapon -> {
          if (!Objects.equals(weapon.getName(), "hand")) {
            list.remove(weapon.getModule(TextureModule.class).getComponent().getEntity());
          }
        });

        if (list.size() >= 1) {
          Entity weaponEntity = list.get(0);

          Weapon weapon = weaponEntity.getComponent(WeaponComponent.class).getWeapon();
          hero.getModule(CreatureWeaponModule.class).changeWeapon(weapon);
        } else {
          hero.getModule(CreatureWeaponModule.class).changeWeapon(WeaponFactory.getWeapon(Weapons.Hand));
        }
      }
    }, KeyCode.F, VirtualButton.X);
    getInput().addAction(new UserAction("Swap weapons") {
      @Override
      protected void onActionBegin() {
        Creature hero = player.getComponent(PlayerComponent.class).getHero();
        if (!hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getName().equals("hand")) {
          hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().setVisible(false);
        }
        Weapon weapon = hero.getModule(CreatureWeaponModule.class).getNextWeapon();
        if (!weapon.getName().equals("hand")) {
          weapon.getModule(TextureModule.class).getComponent().getEntity().setVisible(true);
        }
      }
    }, KeyCode.Q, VirtualButton.B);
  }
}