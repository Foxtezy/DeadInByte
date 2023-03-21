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
import com.almasb.fxgl.entity.components.IDComponent;
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
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;
import ru.nsu.fit.dib.projectdib.multiplayer.ClientTaskManager;
import ru.nsu.fit.dib.projectdib.multiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.multiplayer.data.NewEntity;

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
    ClientTaskManager clientTaskManager = new ClientTaskManager();
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

        Creature hero = player.getComponent(HeroComponent.class).getCreature();
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
    }, Controls.TAKE_THROW_WEAPON, VirtualButton.X);
    getInput().addAction(new UserAction("Swap weapons") {
      @Override
      protected void onActionBegin() {
        Creature hero = player.getComponent(HeroComponent.class).getCreature();
        if (!hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getName().equals("hand")) {
          hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().setVisible(false);
        }
        Weapon weapon = hero.getModule(CreatureWeaponModule.class).getNextWeapon();
        if (!weapon.getName().equals("hand")) {
          weapon.getModule(TextureModule.class).getComponent().getEntity().setVisible(true);
        }
      }
    }, Controls.CHANGE_WEAPON, VirtualButton.B);
  }
}