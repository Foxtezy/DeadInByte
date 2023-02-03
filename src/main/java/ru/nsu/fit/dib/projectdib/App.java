package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.LevelLoader;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.io.FileSystemService;
import com.almasb.fxgl.physics.CollisionHandler;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import ru.nsu.fit.dib.projectdib.level_generation.Level;
import ru.nsu.fit.dib.projectdib.loaderobjects.ChunkLoader;
import ru.nsu.fit.dib.projectdib.loaderobjects.ChunkLoaderComponent;
import ru.nsu.fit.dib.projectdib.mapperobjects.WallMapper;
import ru.nsu.fit.dib.projectdib.moving.components.PlayerMovingComponent;
import ru.nsu.fit.dib.projectdib.tmxbuilder.LevelToTmx;

public class App extends GameApplication {
  Factory factory;
  Viewport viewport;
  private Entity player;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  protected void initSettings(GameSettings settings) {
    settings.setTitle("RDPLS-D2");
    settings.setFileSystemWriteAllowed(false);
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
  }

  public boolean skipOther = false;

  public void setSkipOther(boolean skipOther) {
    this.skipOther = skipOther;
  }

  public boolean isSkipOther() {
    return skipOther;
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
              Entity closedDoor = btn.getObject("closedDoor");
              Entity openedDoor = spawn("openedDoor", closedDoor.getPosition());
              closedDoor.removeFromWorld();
            });
      }
    }, KeyCode.E, VirtualButton.B);


    getInput().addAction(new UserAction("Take") {
      @Override
      protected void onActionBegin() {

        if(!isSkipOther()){
        getGameWorld().getEntitiesByType(EntityType.AK)
                .stream()
                .filter(ak -> ak.hasComponent(CollidableComponent.class) && ak.isColliding(player))
                .forEach(ak -> {
                  spawn(player.getComponent(PlayerMovingComponent.class).getCurrentWeapon(), player.getCenter().subtract(new Point2D(80,100)));
                  player.getComponent(PlayerMovingComponent.class).setCurrentWeapon("ak");
                  ak.removeFromWorld();
                  setSkipOther(true);
                });

        }
        if(!isSkipOther()){
        getGameWorld().getEntitiesByType(EntityType.BOW)
                .stream()
                .filter(bow -> bow.hasComponent(CollidableComponent.class) && bow.isColliding(player))
                .forEach(bow -> {
                  spawn(player.getComponent(PlayerMovingComponent.class).getCurrentWeapon(), player.getCenter().subtract(new Point2D(80,100)));
                  player.getComponent(PlayerMovingComponent.class).setCurrentWeapon("bow");
                  bow.removeFromWorld();
                  setSkipOther(true);
                });
        }


        setSkipOther(false);
      }
    }, KeyCode.F, VirtualButton.X);

    onBtn(MouseButton.PRIMARY, "shoot", () ->  player.getComponent(PlayerMovingComponent.class).shoot());

  }
  @Override
  protected void initPhysics() {
    getPhysicsWorld().setGravity(0, 0);
    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BOX, EntityType.PROJECTILE) {
      @Override
      protected void onCollisionBegin(Entity box, Entity arrow ) {spawn("coin", box.getCenter());  box.removeFromWorld(); arrow.removeFromWorld();}
    });

    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.CHEST, EntityType.PROJECTILE) {
      @Override
      protected void onCollisionBegin(Entity chest, Entity projectile) {
        var hp = chest.getComponent(HealthIntComponent.class);
        if (hp.getValue() > 1){
          projectile.removeFromWorld();
          hp.damage(1);
          return;
        }
        projectile.removeFromWorld();
        spawn("coin", chest.getCenter());
        chest.removeFromWorld();
        projectile.removeFromWorld();}

    });
    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PROJECTILE, EntityType.WALL) {
      @Override
      protected void onCollisionBegin(Entity arrow, Entity wall ) {arrow.removeFromWorld();}
    });

    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
      @Override
      protected void onCollisionBegin(Entity player, Entity coin) {
        coin.removeFromWorld();
      }
    });
    getPhysicsWorld().addCollisionHandler(
        new CollisionHandler(EntityType.PLAYER, EntityType.DOOR_TRIGGER) {
          protected void onCollisionBegin(Entity player, Entity doorTrigger) {
            Entity openedDoor = doorTrigger.getObject("openedDoor");
            Entity closedDoor = spawn("closedDoor", openedDoor.getPosition());
            openedDoor.removeFromWorld();
          }
        });
  }

  @Override
  protected void initGame() {
    viewport = getGameScene().getViewport();
    factory = new Factory();
    getGameWorld().addEntityFactory(factory);

    Level lvl= new Level(new Random().nextInt(),64,64,1,15);
    String levelName = "tmx/" + LevelToTmx.levelToTmx(lvl);
    FXGL.setLevelFromMap(levelName);
    WallMapper wallMapper;
    Level oldLevel;
    try {
      oldLevel = deSerialize();
      wallMapper = new WallMapper(256, 16, oldLevel.map);
      oldLevel.print();
      this.player = spawn("player", (oldLevel.start.getCentrePoint().x - 1) * 16, (oldLevel.start.getCentrePoint().y - 1) * 16);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    try {
      serialize(lvl);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    player.addComponent(new ChunkLoaderComponent(new ChunkLoader(wallMapper)));
    viewport.setZoom(1.2);
    viewport.setLazy(true);



    /*
    FXGL.setLevelFromMap("tmx/level2.tmx");
    Spawn.spawnInitialObjects();
    spawn("enemy", 48, 240);
    this.player = spawn("player", getAppWidth() / 2, getAppHeight() / 2);
    viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    AStarGrid grid = AStarGrid.fromWorld(FXGL.getGameWorld(), FXGLForKtKt.getAppWidth(), getAppHeight(), 25, 25,
        (type) -> {
          if (type == EntityType.WALL || type == EntityType.CLOSED_DOOR) {
            return CellState.NOT_WALKABLE;
          }

          return CellState.WALKABLE;
        });
    set("grid", grid);

    spawn("ak", 600, 600);
    this.player = spawn("player", 60, 60);
    viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    viewport.setLazy(true); */
  }

  private void serialize(Level lvl) throws IOException {
    FileOutputStream fos = new FileOutputStream("level.out");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(lvl);
    oos.flush();
    oos.close();
  }

  private Level deSerialize() throws Exception {
    FileInputStream fis = new FileInputStream("level.out");
    ObjectInputStream oin = new ObjectInputStream(fis);
    return (Level) oin.readObject();
  }
}
