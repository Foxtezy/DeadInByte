package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.UI;
import java.util.List;

import javafx.scene.Node;
import ru.nsu.fit.dib.projectdib.data.Musics;
import ru.nsu.fit.dib.projectdib.initapp.GameInitializer;
import ru.nsu.fit.dib.projectdib.initapp.InputListener;
import ru.nsu.fit.dib.projectdib.initapp.PhysicsLoader;
import ru.nsu.fit.dib.projectdib.initapp.SettingsLoader;
import ru.nsu.fit.dib.projectdib.ui.GameUIController;
import ru.nsu.fit.dib.projectdib.utils.BackgroundMusicController;


/**
 * Основной класс игры.
 */
public class App extends GameApplication {

  public static Entity player;
  InputListener inputListener;
  public static GameUIController uiController;

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

  @Override
  protected void initUI() {
    uiController = new GameUIController(getGameScene());
    UI ui =  getAssetLoader().loadUI("main.fxml", uiController);
    FXGL.getGameScene().addUI(ui);
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
    player = gameInitializer.getPlayer();
    BackgroundMusicController.getBackgroundMusicControlleroller().setPlaylist(List.of(Musics.first_music,Musics.second_music,Musics.third_music));
  }

  @Override
  protected void onUpdate(double tpf) {
    List<Entity> list = FXGL.getGameScene().getGameWorld()
        .getEntitiesByType(EntityType.PLAYER, EntityType.ENEMY, EntityType.WEAPON);
    list.sort((e1, e2) -> (int) (e1.getY() - e2.getY()));
    list.stream().map(entity -> entity.getViewComponent().getParent()).forEach(Node::toFront);
    BackgroundMusicController.getBackgroundMusicControlleroller().onUpdate();
  }
}
