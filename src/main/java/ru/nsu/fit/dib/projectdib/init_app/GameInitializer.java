package ru.nsu.fit.dib.projectdib.init_app;

import static com.almasb.fxgl.dsl.FXGL.byID;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.IDComponent;
import java.awt.Paint;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.data.RandomCharacterSystem;
import ru.nsu.fit.dib.projectdib.entity.moving.components.PlayerComponent;
import ru.nsu.fit.dib.projectdib.entity.moving.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.environment.levelLoader.LevelSetter;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoader;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.ChunkLoaderComponent;
import ru.nsu.fit.dib.projectdib.environment.mapperobjects.WallMapper;
import ru.nsu.fit.dib.projectdib.environment.tmxbuilder.LevelToTmx;
import ru.nsu.fit.dib.projectdib.multiplayer.ClientTaskManager;
import ru.nsu.fit.dib.projectdib.multiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.multiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.multiplayer.data.NewEntity;

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

    Weapon myWeapon = player.getComponent(PlayerComponent.class).getHero().getActiveWeapon();
    //List<> list  = player.getViewComponent().visibleProperty();
    //player.getViewComponent().getChildren().remove(player);
    SpawnData wsd = new SpawnData(x, y);
    wsd.put("weapon",myWeapon);
    Entity weapon = spawn("weapon",wsd);
    weapon.getComponent(WeaponComponent.class).getWeapon();

    SpawnData wsd2 = new SpawnData(x, y);
    wsd2.put("weapon", WeaponFactory.getWeapon(Weapons.Sword));
    spawn("weapon",wsd2);

    //player.getComponent(PlayerComponent.class).getHero().getActiveWeapon().getComponent().getEntity();
    weapon.xProperty().bind(player.xProperty());
    weapon.yProperty().bind(player.yProperty());

    player.setScaleUniform(0.75);
    player.addComponent(new ChunkLoaderComponent(new ChunkLoader(wallMapper)));
    //FXGL.getGameWorld().getEntities().add(1,player);
    //System.out.println(player.getComponent(IDComponent.class));
    viewport.setWidth(getAppWidth());
    viewport.setHeight(getAppHeight());
    viewport.setZoom(0.75);
    viewport.focusOn(player);
    viewport.setBounds(0, 0, 64 * 160, 64 * 160);
    viewport.bindToEntity(player, viewport.getWidth() / 2, viewport.getHeight() / 2);
    viewport.setLazy(true);
  }

  public Entity getPlayer() {
    return player;
  }
}
