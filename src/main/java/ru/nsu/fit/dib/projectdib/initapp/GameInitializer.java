package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.data.RandomCharacterSystem;
import ru.nsu.fit.dib.projectdib.entity.components.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.environment.levelLoader.LevelSetter;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoader;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoaderComponent;
import ru.nsu.fit.dib.projectdib.environment.mapperobjects.WallMapper;
import ru.nsu.fit.dib.projectdib.environment.tmxbuilder.LevelToTmx;
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

  public GameInitializer() {
  }

  public void run() {
    viewport = getGameScene().getViewport();
    factory = new Factory();
    getGameWorld().addEntityFactory(factory);

    Level lvl = new Level(12345, 64, 64, 1, 15);
    String levelName = "levels/" + LevelToTmx.levelToTmx(lvl);
    LevelSetter.setLevelFromMap(levelName, getGameWorld());
    WallMapper wallMapper = new WallMapper(2560, 160, lvl.map);
    //lvl.print()
    Entity weapon;
    double x = (lvl.start.getCentrePoint().x) * 160;
    double y = (lvl.start.getCentrePoint().y) * 160;
    Point2D position = new Point2D(x,y);
    try {
      System.out.println(HeroType.Elf.getName());
      player = EntitySpawner.spawn(new NewEntity(HeroType.Elf.getName(),123,position,null)).get();
    } catch (ExecutionException | InterruptedException e) {
      throw new RuntimeException(e);
    }
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
