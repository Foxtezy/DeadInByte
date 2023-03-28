package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.onBtn;
import static com.almasb.fxgl.dsl.FXGL.onBtnDown;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.onKeyUp;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.data.Controls;
import ru.nsu.fit.dib.projectdib.data.json.update.Action;
import ru.nsu.fit.dib.projectdib.data.json.update.ActionType;
import ru.nsu.fit.dib.projectdib.entity.components.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;

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
    onKey(Controls.UP, "Up", () -> player.getComponent(HeroComponent.class).up());
    onKey(Controls.LEFT, "Left", () -> player.getComponent(HeroComponent.class).left());
    onKey(Controls.DOWN, "Down", () -> player.getComponent(HeroComponent.class).down());
    onKey(Controls.RIGHT, "Right", () -> player.getComponent(HeroComponent.class).right());
    onKey(KeyCode.R, "Unbind", () -> {
      Creature hero = player.getComponent(HeroComponent.class).getCreature();
      hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().xProperty().unbind();
      hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().yProperty().unbind();
    });
    onBtn(MouseButton.PRIMARY, "shoot", () -> player.getComponent(HeroComponent.class).attack());
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
        HeroComponent playerComponent = player.getComponent(HeroComponent.class);
        List<Entity> list = playerComponent.findWeapon();

        //----------------------------------------
        if (list.size() >= 1) {
          //Спрашиваем сервер можно ли забрать Weapon
          player.getComponent(DataComponent.class).addAction(new Action(ActionType.TAKE,list.get(0).getComponent(DataComponent.class).getId()));
          //если да то:
          //playerComponent.takeWeapon(list.get(0));
        } else {
          player.getComponent(DataComponent.class).addAction(new Action(ActionType.THROW,list.get(0).getComponent(DataComponent.class).getId()));
          //Если Weapon рядом нет то прашиваем можно ли выбрость:
          //playerComponent.throwWeapon();
        }
        //Вообще эту часть нужно будет убрать тк действие будет совершаться из распакованного JSON-а
        //------------------------------------------
      }
    }, Controls.TAKE_THROW_WEAPON, VirtualButton.X);

    getInput().addAction(new UserAction("Swap weapons") {
      @Override
      protected void onActionBegin() {
        player.getComponent(HeroComponent.class).swapWeapon();
      }
    }, Controls.CHANGE_WEAPON, VirtualButton.B);
  }
}