package ru.nsu.fit.dib.projectdib.init_app;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.onBtn;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.moving.components.PlayerMovingComponent;

public class InputListener {
  public boolean skipOther = false;
  private Entity player;
  public InputListener(){
  }
  public boolean isSkipOther() {
    return skipOther;
  }

  public void setSkipOther(boolean skipOther) {
    this.skipOther = skipOther;
  }
  public void initialize(Entity player){
    this.player=player;
  }
  public void run(){
    onKey(KeyCode.A, "Left", () -> player.getComponent(PlayerMovingComponent.class).left());
    onKey(KeyCode.D, "Right", () -> player.getComponent(PlayerMovingComponent.class).right());
    onKey(KeyCode.W, "up", () -> player.getComponent(PlayerMovingComponent.class).up());
    onKey(KeyCode.S, "Down", () -> player.getComponent(PlayerMovingComponent.class).down());
    onKey(KeyCode.X, "SwapWeapon", () -> player.getComponent(PlayerMovingComponent.class).swapWeapons());
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
