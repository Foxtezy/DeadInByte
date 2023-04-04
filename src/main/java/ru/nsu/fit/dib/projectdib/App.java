package ru.nsu.fit.dib.projectdib;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;import com.almasb.fxgl.dsl.FXGLForKtKt;import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.pathfinding.CellState;import com.almasb.fxgl.pathfinding.astar.AStarGrid;import ru.nsu.fit.dib.projectdib.initapp.GameInitializer;
import ru.nsu.fit.dib.projectdib.initapp.InputListener;
import ru.nsu.fit.dib.projectdib.initapp.PhysicsLoader;
import ru.nsu.fit.dib.projectdib.initapp.SettingsLoader;import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

/**
 * Основной класс игры.
 */
public class App extends GameApplication {

  Entity player;
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
    player=gameInitializer.getPlayer();
    AStarGrid grid = AStarGrid.fromWorld(FXGL.getGameWorld(), FXGLForKtKt.getAppWidth(), getAppHeight(), 160, 160,
            (type) -> {
              if (type == EntityType.WALL || type == EntityType.CLOSED_DOOR) {
                return CellState.NOT_WALKABLE;
              }

              return CellState.WALKABLE;
            });
    set("grid", grid);
  }
}
