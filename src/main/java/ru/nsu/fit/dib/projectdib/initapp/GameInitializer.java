package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._character_file;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import java.util.concurrent.ExecutionException;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.data.RandomCharacterSystem;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.data.HeroData;
import ru.nsu.fit.dib.projectdib.data.JSONController;
import ru.nsu.fit.dib.projectdib.data.RandomCharacterSystem;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoader;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoaderComponent;
import ru.nsu.fit.dib.projectdib.environment.mapperobjects.WallMapper;
import ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;

/**
 * Инициализатор игры.
 */
public class GameInitializer {

  private Factory factory;
  private Viewport viewport;
  private Entity player;
  public static AStarGrid grid;
  public static Point2D start;
  public static ChunkLoaderComponent chunkLoader;

  public GameInitializer() {
  }
  public static Level lvl;
  public void run() {
    viewport = getGameScene().getViewport();
    factory = new Factory();
    getGameWorld().addEntityFactory(factory);
    MapInitializer mapInitializer = new MapInitializer();
    lvl = mapInitializer.run();
    MultiplayerInitializer multiplayerInitializer = new MultiplayerInitializer();
    multiplayerInitializer.run();
    WallMapper wallMapper = new WallMapper(2560, 160, lvl.map, grid);
    chunkLoader = new ChunkLoaderComponent(new ChunkLoader(wallMapper));
    if (MCClient.getClientId() == 1) {
      GridCreator gridCreator = new GridCreator(lvl.map);
      gridCreator.run();
    }
    double x = (lvl.start.getCentrePoint().x) * 160;
    double y = (lvl.start.getCentrePoint().y) * 160;
    start = new Point2D(x,y);
    HeroData data = JSONController.<HeroData>load("src/main/resources/saves/character.json",HeroData.class);
    if (data==null || data.getType()==null || data.getSeed()==null) {
      data = new HeroData(RandomCharacterSystem.NewCharacter(), RandomSystem.getRandInt(10000));
      JSONController.save(_character_file,data);
    }
    try {
      player = EntitySpawner.spawn(new NewEntity(data.getType().getName(),data.getSeed(),start,null)).get();
    } catch (ExecutionException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    //===================================
    //SpawnData sd2 = new SpawnData(x,y);
    //sd2.put("creature", RandomCharacterSystem.NewCharacter());
    //spawn("player", sd2);
    //===================================
    viewport.setWidth(getAppWidth());
    viewport.setHeight(getAppHeight());
    viewport.setZoom(0.75);
    viewport.setBounds(0, 0, 65 * 160, 65 * 160);
    viewport.setLazy(true);
  }

  public Entity getPlayer() {
    return player;
  }
}
