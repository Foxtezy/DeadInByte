package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.set;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.lengthOfCell;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.PhysicsComponent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.BlockDensity;
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
    // мультиплейерная часть
    MapInitializer mapInitializer = new MapInitializer();
    Level lvl = mapInitializer.run();
    MultiplayerInitializer multiplayerInitializer = new MultiplayerInitializer();
    multiplayerInitializer.run();
    // мультиплейерная часть
    System.out.println("appHeight " + getAppHeight() + "appWidth " + getAppWidth());
      grid = AStarGrid.fromWorld(getGameWorld(), 66, 66, lengthOfCell, lengthOfCell,
              (entityType) -> {
              /*    if (entityType != EntityType.WALL) {
                      return CellState.WALKABLE;
                  }*/
                  return CellState.NOT_WALKABLE;
              });
    WallMapper wallMapper = new WallMapper(2560, 160, lvl.map, grid);
    System.out.println("lvl.map.length " + lvl.map.length + "lvl.map[0].length" +  lvl.map[0].length);
    int counter = 0;
    int counterOFANY = 0;
    for(int x = 0; x < lvl.map.length ; x++){
      for(int y = 1; y < lvl.map[x].length; y++){
        if(lvl.map[x][y] != BlockDensity.WALL.density) {
          counter++;
          grid.set(x,y-1, new AStarCell(x,y-1,CellState.WALKABLE));

        }

        counterOFANY++;
      }
    }

      List<AStarCell> nonWalkable = grid.getCells().stream().filter(cell -> !cell.isWalkable()).toList();
    System.out.println(nonWalkable);
    set("grid", grid);
    boolean t = true;
    for (int y = 0; y < 65; y++) {
      for (int x = 0; x < 65; x++) {
        char c = '.';
        if (grid.get(y,x).isWalkable() && lvl.map[y][x] != BlockDensity.WALL.density) {
          c = '#';
        }
          if (!grid.get(y,x).isWalkable() && lvl.map[y][x] == BlockDensity.WALL.density) {
            c = '#';
          }
        if (grid.get(y,x).isWalkable() && lvl.map[y][x] == BlockDensity.WALL.density) {
          c = '!';
          t = false;
        }
        if (!grid.get(y,x).isWalkable() && lvl.map[y][x] != BlockDensity.WALL.density) {
          c = '&';
          t = false;
        }

        System.out.printf("%2c", c);
      }
      System.out.println();
    }
    System.out.println("t = " + t);

    System.out.println("CELLS IN GRID = " + grid.getCells().size());
    System.out.println("COUNT of NON_WALKABLE: " + nonWalkable.size());
    System.out.println("COUNTER OF WALLS: "+ counter);
    System.out.println("COUNTER ALL in LVL.map: " + counterOFANY);
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
    viewport.setZoom(0.25);
    viewport.focusOn(player);
    viewport.setBounds(0, 0, 64 * 160, 64 * 160);
    viewport.bindToEntity(player, viewport.getWidth() / 2-40, viewport.getHeight() / 2-120);
    viewport.setLazy(true);
  }

  public Entity getPlayer() {
    return player;
  }
}
