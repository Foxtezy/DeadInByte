package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.PhysicsComponent;
import java.util.concurrent.ExecutionException;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoader;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoaderComponent;
import ru.nsu.fit.dib.projectdib.environment.mapperobjects.WallMapper;
import ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;

/**
 * Инициализатор игры.
 */
public class GameInitializer {

  private Factory factory;
  private Viewport viewport;
  private Entity player;
  public static AStarGrid grid;

  public GameInitializer() {
  }

  public void run() {
    viewport = getGameScene().getViewport();
    factory = new Factory();
    getGameWorld().addEntityFactory(factory);
    MapInitializer mapInitializer = new MapInitializer();
    Level lvl = mapInitializer.run();
    MultiplayerInitializer multiplayerInitializer = new MultiplayerInitializer();
    multiplayerInitializer.run();
    WallMapper wallMapper = new WallMapper(2560, 160, lvl.map, grid);
    GridCreator gridCreator = new GridCreator(lvl.map);
    gridCreator.run();
    double x = (lvl.start.getCentrePoint().x) * 160;
    double y = (lvl.start.getCentrePoint().y) * 160;
    Point2D position = new Point2D(x,y);
    try {
      System.out.println(HeroType.Elf.getName());
      player = EntitySpawner.spawn(new NewEntity(HeroType.Knight.getName(),123,position,null)).get();

   //   set("grid", grid);
   //  System.out.println("CELLS " + grid.getCells().stream().filter(cell -> !cell.isWalkable()).toList());
      Entity enemy = EntitySpawner.spawn(new NewEntity(EnemiesFactory.EnemyType.Devil.getName(), 456, position.subtract(new Point2D(160, 320)), null)).get();
      enemy.getComponent(PhysicsComponent.class).setLinearVelocity(100, 100);
      //System.out.println(player.getComponent(HeroComponent.class).getCreature().getSpeed());
    } catch (ExecutionException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("e");
    player.addComponent(new ChunkLoaderComponent(new ChunkLoader(wallMapper)));
    //===================================
    //SpawnData sd2 = new SpawnData(x,y);
    //sd2.put("creature", RandomCharacterSystem.NewCharacter());
    //spawn("player", sd2);
    //===================================
    viewport.setWidth(getAppWidth());
    viewport.setHeight(getAppHeight());
    viewport.setZoom(0.75);
    viewport.focusOn(player);
    viewport.setBounds(0, 0, 64 * 160, 64 * 160);
    viewport.bindToEntity(player, viewport.getWidth() / 2-40, viewport.getHeight() / 2-120);
    viewport.setLazy(true);
  }

  public Entity getPlayer() {
    return player;
  }
}
