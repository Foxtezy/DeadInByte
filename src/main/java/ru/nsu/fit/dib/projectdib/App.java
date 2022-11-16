package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import ru.nsu.fit.dib.projectdib.level_generation.GenerationMethods.BinaryPartition.Structures.BPGraph;
import ru.nsu.fit.dib.projectdib.level_generation.GenerationMethods.BinaryPartition.Structures.BPLeaf;
import ru.nsu.fit.dib.projectdib.level_generation.GenerationMethods.BinaryPartition.BinaryPartitionLG;
import ru.nsu.fit.dib.projectdib.level_generation.Level;
import ru.nsu.fit.dib.projectdib.level_generation.Way.FindWay;
import ru.nsu.fit.dib.projectdib.moving.components.PlayerMovingComponent;

public class App extends GameApplication {
  Factory factory;
  Viewport viewport;
  private Entity player;

  public static void main(String[] args) {
    //testing
    String p="src/main/resources/assets/levels/texture_pallettes/new_palette.json";
    Level lvl= new Level(99812,64,64);
    BinaryPartitionLG partition= new BinaryPartitionLG(lvl,4,0);
    BPLeaf tree = new BPLeaf(new Point(0,0), new Point(64,64));
    partition.setPartition(tree);
    BPGraph graph = new BPGraph(tree);
    lvl.tileType=20;
    partition.printPartition(tree);
    FindWay way=new FindWay(lvl);
    way.findWay(new Point(1,1),new Point(40,35));
    way.printWay();
    lvl.print();
    lvl.tileType=20;
    //testing
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

    onKey(KeyCode.A,"Left",() -> player.getComponent(PlayerMovingComponent.class).left() );
    onKey(KeyCode.D,"Right",() -> player.getComponent(PlayerMovingComponent.class).right() );
    onKey(KeyCode.W,"up",() -> player.getComponent(PlayerMovingComponent.class).up() );
    onKey(KeyCode.S,"Down",() -> player.getComponent(PlayerMovingComponent.class).down() );
    getInput().addAction(new UserAction("Use") {
      @Override
      protected void onActionBegin() {
        getGameWorld().getEntitiesByType(EntityType.BUTTON)
            .stream()
            .filter(btn -> btn.hasComponent(CollidableComponent.class) && player.isColliding(btn))
            .forEach(btn -> {
              btn.removeComponent(CollidableComponent.class);
              var closedDoor = getGameWorld().getSingleton(EntityType.CLOSED_DOOR);
              closedDoor.removeFromWorld();
              spawn("openedDoor", 144, 192);
            });
      }
    }, KeyCode.E, VirtualButton.B);


    onBtn(MouseButton.PRIMARY, "shoot", () ->  player.getComponent(PlayerMovingComponent.class).shoot());

  }
  @Override
  protected void initPhysics() {
    getPhysicsWorld().setGravity(0, 0);
    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BOX, EntityType.ARROW) {
      @Override
      protected void onCollisionBegin(Entity box, Entity arrow ) {box.removeFromWorld(); arrow.removeFromWorld();}
    });
    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ARROW, EntityType.WALL) {
      @Override
      protected void onCollisionBegin(Entity arrow, Entity wall ) {arrow.removeFromWorld();}
    });

    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
      @Override
      protected void onCollisionBegin(Entity player, Entity coin) {
        coin.removeFromWorld();
      }
    });
    onCollisionOneTimeOnly(EntityType.PLAYER, EntityType.DOOR_TRIGGER, (player, trigger) -> {
      var openedDoor = getGameWorld().getSingleton(EntityType.OPENED_DOOR);
      openedDoor.removeFromWorld();
      spawn("closedDoor", 144, 192);
    });
  }

  // Спавн существ
  @Override
  protected void initGame() {
    viewport = getGameScene().getViewport();
    factory = new Factory();

    getGameWorld().addEntityFactory(factory);
    FXGL.setLevelFromMap("tmx/level2.tmx");


    this.player = spawn("player", 60, 60);
    viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    viewport.setLazy(true);
  }
}
