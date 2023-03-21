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
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.data.RandomCharacterSystem;
import ru.nsu.fit.dib.projectdib.entity.components.PlayerComponent;
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

    Level lvl = new Level(new Random().nextInt(), 64, 64, 1, 15);
    String levelName = "levels/" + LevelToTmx.levelToTmx(lvl);
    LevelSetter.setLevelFromMap(levelName, getGameWorld());
    WallMapper wallMapper = new WallMapper(2560, 160, lvl.map);
    //lvl.print()

    double x = (lvl.start.getCentrePoint().x) * 160;
    double y = (lvl.start.getCentrePoint().y) * 160;
    SpawnData sd = new SpawnData(x,y);
    sd.put("creature", RandomCharacterSystem.NewCharacter());
    player = spawn("player", sd);

    Weapon myWeapon = player.getComponent(PlayerComponent.class).getHero().getModule(
        CreatureWeaponModule.class).getActiveWeapon();
    SpawnData wsd = new SpawnData(x, y);
    wsd.put("weapon",myWeapon);
    Entity weapon = spawn("weapon",wsd);

    SpawnData wsd2 = new SpawnData(x, y);
    wsd2.put("weapon", WeaponFactory.getWeapon(Weapons.Sword));
    spawn("weapon",wsd2);

    player.setScaleUniform(0.75);
    player.addComponent(new ChunkLoaderComponent(new ChunkLoader(wallMapper)));

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
