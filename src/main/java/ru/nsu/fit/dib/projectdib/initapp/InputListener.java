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
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.components.PlayerComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.WeaponModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
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
    onKeyUp(KeyCode.P, () -> {
      Point2D p1 = player.getPosition().add(new Point2D(5, 5));
      List<NewEntity> nel = new ArrayList<>();
      nel.add(new NewEntity(1, "box", new SpawnData(p1)));
      clientTaskManager.spawnEntities(nel);
      System.out.println(clientTaskManager.getIdHashTable().get(1) + " spawned");
      System.out.println("with ID" + clientTaskManager.getIdHashTable().get(1).getComponent(
          IDComponent.class).getId());
    });
    onKeyUp(KeyCode.L, () -> {
      Point2D p2 = clientTaskManager.getIdHashTable().get(1).getPosition().add(new Point2D(5, 5));
      List<EntityState> nes = new ArrayList<>();
      nes.add(new EntityState(1, p2, 0.0));
      clientTaskManager.updateEntities(nes);
      System.out.println(clientTaskManager.getIdHashTable().get(1) + " moved");
      System.out.println("with ID" + clientTaskManager.getIdHashTable().get(1).getComponent(
          IDComponent.class).getId());
    });
    //==============================================================================================
    onKey(KeyCode.W, "Up", () -> player.getComponent(PlayerComponent.class).up());
    onKey(KeyCode.A, "Left", () -> player.getComponent(PlayerComponent.class).left());
    onKey(KeyCode.S, "Down", () -> player.getComponent(PlayerComponent.class).down());
    onKey(KeyCode.D, "Right", () -> player.getComponent(PlayerComponent.class).right());
    onKey(KeyCode.R, "Unbind", () -> {
      Creature hero = player.getComponent(PlayerComponent.class).getHero();
      hero.getModule(WeaponModule.class).getActiveWeapon().getComponent().getEntity().xProperty().unbind();
      hero.getModule(WeaponModule.class).getActiveWeapon().getComponent().getEntity().yProperty().unbind();
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
        hero.getModule(WeaponModule.class).getWeaponsList().forEach(weapon -> {
          if (!Objects.equals(weapon.getName(), "hand")) {
            list.remove(weapon.getComponent().getEntity());
          }
        });

        if (list.size() >= 1) {
          Entity weaponEntity = list.get(0);

          Weapon weapon = weaponEntity.getComponent(WeaponComponent.class).getWeapon();
          hero.getModule(WeaponModule.class).changeWeapon(weapon);
        } else {
          hero.getModule(WeaponModule.class).changeWeapon(WeaponFactory.getWeapon(Weapons.Hand));
        }
      }
    }, KeyCode.F, VirtualButton.X);
    getInput().addAction(new UserAction("Swap weapons") {
      @Override
      protected void onActionBegin() {
        Creature hero = player.getComponent(PlayerComponent.class).getHero();
        if (!hero.getModule(WeaponModule.class).getActiveWeapon().getName().equals("hand")) {
          hero.getModule(WeaponModule.class).getActiveWeapon().getComponent().getEntity().setVisible(false);
        }
        Weapon weapon = hero.getModule(WeaponModule.class).getNextWeapon();
        if (!weapon.getName().equals("hand")) {
          weapon.getComponent().getEntity().setVisible(true);
        }
      }
    }, KeyCode.Q, VirtualButton.B);
  }
}