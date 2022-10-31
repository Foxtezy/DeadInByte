package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import java.awt.Dimension;
import java.awt.Toolkit;

import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.input.KeyCode;
import ru.nsu.fit.dib.projectdib.moving.components.PlayerMovingComponent;

public class App extends GameApplication {

  Factory factory;
  Viewport viewport;
  private Entity player;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  protected void initSettings(GameSettings settings) {
    settings.setDeveloperMenuEnabled(true);
    settings.setApplicationMode(ApplicationMode.DEVELOPER);
    Config.setConfig("src/main/resources/cfg.ini");
    // Window mod
    switch (Config.WINDOW_MODE) {
      case "Window" -> {
        settings.setWidth(Config.WINDOW_WIDTH);
        settings.setHeight(Config.WINDOW_HEIGHT);
      }
      case "Borderless" -> {
        System.out.print(Config.WINDOW_MODE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        settings.setHeight(dimension.height);
        settings.setWidth(dimension.width);
      }
      case "Full-screen" -> {
        settings.setWidth(Config.WINDOW_WIDTH);
        settings.setHeight(Config.WINDOW_HEIGHT);
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
      }
      default -> {
        settings.setWidth(600);
        settings.setHeight(600);
      }
    }
    // Title
    settings.setTitle("DiB");
  }

  // Управление
  @Override
  protected void initInput() {

    getInput().addAction(new UserAction("Left") {
      @Override
      protected void onAction() {
        player.getComponent(PlayerMovingComponent.class).left();
      }

    }, KeyCode.A, VirtualButton.LEFT);

    getInput().addAction(new UserAction("Right") {
      @Override
      protected void onAction() {
        player.getComponent(PlayerMovingComponent.class).right();
      }

    }, KeyCode.D, VirtualButton.RIGHT);

    getInput().addAction(new UserAction("Up") {
      @Override
      protected void onAction() {
        player.getComponent(PlayerMovingComponent.class).up();
      }

    }, KeyCode.W, VirtualButton.UP);

    getInput().addAction(new UserAction("Down") {
      @Override
      protected void onAction() {
        player.getComponent(PlayerMovingComponent.class).down();
      }

    }, KeyCode.S, VirtualButton.DOWN);

  getInput().addAction(new UserAction("Kick") {
    protected void onActionBegin(){
      Entity key = getGameWorld().getEntitiesByType(EntityType.BOX).get(0);

      PhysicsComponent physics = key.getComponent(PhysicsComponent.class);
      physics.applyForceToCenter(player.getCenter().subtract(key.getCenter()).normalize().multiply(1000));
    }

    }, KeyCode.E);
  }

  @Override
  protected void initPhysics() {
    getPhysicsWorld().setGravity(0, 0);
    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
      @Override
      protected void onCollisionBegin(Entity player, Entity coin) {
        coin.removeFromWorld();
      }
    });
  }

  // Спавн существ
  @Override
  protected void initGame() {
    viewport = getGameScene().getViewport();
    factory = new Factory();

    getGameWorld().addEntityFactory(factory);
    FXGL.setLevelFromMap("tmx/level2.tmx");

    this.player = spawn("player", getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);
    viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
  }
}
