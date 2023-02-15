package ru.nsu.fit.dib.projectdib;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import ru.nsu.fit.dib.projectdib.init_app.GameInitializer;
import ru.nsu.fit.dib.projectdib.init_app.InputListener;
import ru.nsu.fit.dib.projectdib.init_app.PhysicsLoader;
import ru.nsu.fit.dib.projectdib.init_app.SettingsLoader;

/**
 * Основной класс игры.
 */
public class App extends GameApplication {

  InputListener inputListener;

  /**
   * Main-метод.
   *
   * @param args - аргументы командной строки
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Инициализация настроек игры.
   *
   * @param settings - настройки
   */
  @Override
  protected void initSettings(GameSettings settings) {
    SettingsLoader settingsLoader = new SettingsLoader(settings);
    settingsLoader.run();
  }

  /**
   * Инициализация действий со входными данными(например, с клавиатуры)
   */
  @Override
  protected void initInput() {
    inputListener = new InputListener();
    inputListener.run();
  }

  /**
   * Инициализация физики игры.
   */
  @Override
  protected void initPhysics() {
    PhysicsLoader physicsLoader = new PhysicsLoader();
    physicsLoader.run();
  }

  /**
   * Инициализация игры.
   */
  @Override
  protected void initGame() {
    GameInitializer gameInitializer = new GameInitializer();
    gameInitializer.run();
    inputListener.initialize(gameInitializer.getPlayer());
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
    //this.player = spawn("player", 60, 60);
    viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    viewport.setLazy(true);
    */
  }
}
