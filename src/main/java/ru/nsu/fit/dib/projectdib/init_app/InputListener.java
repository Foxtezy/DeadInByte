package ru.nsu.fit.dib.projectdib.init_app;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.onBtn;
import static com.almasb.fxgl.dsl.FXGL.onBtnDown;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.onKeyUp;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IDComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.moving.components.PlayerMovingComponent;
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
    onKeyUp(KeyCode.P,()->{
      Point2D p1 = player.getPosition().add(new Point2D(5,5));
      List<NewEntity> nel = new ArrayList<>();
      nel.add(new NewEntity(1, "box", new SpawnData(p1)));
      clientTaskManager.spawnEntities(nel);
      System.out.println(clientTaskManager.getIdHashTable().get(1)+" spawned");
      System.out.println("with ID"+clientTaskManager.getIdHashTable().get(1).getComponent(
          IDComponent.class).getId());
    });
    onKeyUp(KeyCode.L,()->{
      Point2D p2 = clientTaskManager.getIdHashTable().get(1).getPosition().add(new Point2D(5,5));
      List<EntityState> nes = new ArrayList<>();
      nes.add(new EntityState(1,p2, new Point2D(0,0)));
      clientTaskManager.updateEntities(nes);
      System.out.println(clientTaskManager.getIdHashTable().get(1)+" moved");
      System.out.println("with ID"+clientTaskManager.getIdHashTable().get(1).getComponent(
          IDComponent.class).getId());
    });

    onKey(KeyCode.A, "Left", () -> player.getComponent(PlayerMovingComponent.class).left());
    onKey(KeyCode.D, "Right", () -> player.getComponent(PlayerMovingComponent.class).right());
    onKey(KeyCode.W, "up", () -> player.getComponent(PlayerMovingComponent.class).up());
    onKey(KeyCode.S, "Down", () -> player.getComponent(PlayerMovingComponent.class).down());
    onKey(KeyCode.X, "SwapWeapon",
        () -> player.getComponent(PlayerMovingComponent.class).swapWeapons());
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

        if (!isSkipOther()) {
          getGameWorld().getEntitiesByType(EntityType.AK)
              .stream()
              .filter(ak -> ak.hasComponent(CollidableComponent.class) && ak.isColliding(player))
              .forEach(ak -> {
                spawn(player.getComponent(PlayerMovingComponent.class).getSpecification()
                    .getMainWeapon(), player.getCenter().subtract(new Point2D(80, 100)));
                player.getComponent(PlayerMovingComponent.class).getSpecification()
                    .setMainWeapon("ak");
                ak.removeFromWorld();
                setSkipOther(true);
              });

        }
        if (!isSkipOther()) {
          getGameWorld().getEntitiesByType(EntityType.BOW)
              .stream()
              .filter(bow -> bow.hasComponent(CollidableComponent.class) && bow.isColliding(player))
              .forEach(bow -> {
                spawn(player.getComponent(PlayerMovingComponent.class).getSpecification()
                    .getMainWeapon(), player.getCenter().subtract(new Point2D(80, 100)));
                player.getComponent(PlayerMovingComponent.class).getSpecification()
                    .setMainWeapon("bow");
                bow.removeFromWorld();
                setSkipOther(true);
              });
        }
        setSkipOther(false);
      }
    }, KeyCode.F, VirtualButton.X);

    onBtn(MouseButton.PRIMARY, "shoot",
        () -> player.getComponent(PlayerMovingComponent.class).shoot());

  }
}
