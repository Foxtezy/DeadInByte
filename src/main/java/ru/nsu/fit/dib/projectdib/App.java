package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.UI;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javafx.scene.Node;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.data.Musics;
import ru.nsu.fit.dib.projectdib.initapp.GameInitializer;
import ru.nsu.fit.dib.projectdib.initapp.InputListener;
import ru.nsu.fit.dib.projectdib.initapp.PhysicsLoader;
import ru.nsu.fit.dib.projectdib.initapp.SettingsLoader;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;
import ru.nsu.fit.dib.projectdib.ui.GameUIController;
import ru.nsu.fit.dib.projectdib.utils.BackgroundMusicController;


/**
 * Основной класс игры.
 */
public class App extends GameApplication {
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
    if (MCClient.getClientId() == 1) {
      Sender sender = new Sender();
      MCServer.getClientSockets().entrySet().stream().filter(e -> e.getKey() != 1)
          .forEach(s -> {
            try {
              sender.send(s.getValue(), new Pair<>(MessageType.START_GAME, null));
            } catch (IOException ex) {
            }
          });
      MCServer.getConnectionThread().startGame();
    }
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
    BackgroundMusicController.getBackgroundMusicControlleroller().setPlaylist(List.of(Musics.first_music,Musics.second_music,Musics.third_music));
  }

  public static void stop() {
    MCClient.getClientSenderThread().interrupt();
    MCClient.getClientReceiverThread().interrupt();
    try {
      MCClient.getClientSocket().close();
      MCClient.getClientSenderThread().join();
      MCClient.getClientReceiverThread().join();
    } catch (InterruptedException e) {
    } catch (IOException e) {
    }
    if (MCClient.getClientId() == 1) {
      MCServer.getUpdaterThread().interrupt();
      MCServer.getActionThread().interrupt();
      MCServer.getReceiverThreads().forEach(Thread::interrupt);
      MCServer.getConnectionThread().interrupt();
      try {
        MCServer.getUpdaterThread().join();
        MCServer.getActionThread().join();
        for (Socket socket : MCServer.getClientSockets().values()) {
          socket.close();
        }
        for (Thread t : MCServer.getReceiverThreads()) {
          t.join();
        }
      } catch (InterruptedException e) {
      } catch (IOException e) {
      }
    }
    MCServer.clearContext();
    MCClient.clearContext();
    GameUIController.queue.clear();
    App.uiController = null;
    FXGL.getGameController().gotoMainMenu();
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
