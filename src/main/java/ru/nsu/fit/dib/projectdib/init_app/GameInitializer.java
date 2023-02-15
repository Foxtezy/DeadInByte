package ru.nsu.fit.dib.projectdib.init_app;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import java.util.Random;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.environment.levelLoader.LevelSetter;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoader;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoaderComponent;
import ru.nsu.fit.dib.projectdib.environment.mapperobjects.WallMapper;
import ru.nsu.fit.dib.projectdib.environment.tmxbuilder.LevelToTmx;

public class GameInitializer {
  private Factory factory;
  private Viewport viewport;
  private Entity player;
  public GameInitializer(){
  }
  public void run(){
    viewport = getGameScene().getViewport();
    factory = new Factory();
    getGameWorld().addEntityFactory(factory);

    Level lvl = new Level(new Random().nextInt(), 64, 64, 1, 15);
    String levelName = "levels/" + LevelToTmx.levelToTmx(lvl);
    LevelSetter.setLevelFromMap(levelName, getGameWorld());
    WallMapper wallMapper = new WallMapper(256, 16, lvl.map);
    //lvl.print();
    player = spawn("player", (lvl.start.getCentrePoint().x - 1) * 16,
        (lvl.start.getCentrePoint().y - 1) * 16);
    viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    player.addComponent(new ChunkLoaderComponent(new ChunkLoader(wallMapper)));
    viewport.setZoom(3);
    viewport.setLazy(true);
  }
  public Entity getPlayer(){
    return player;
  }
}
