package ru.nsu.fit.dib.projectdib;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.initapp.GameInitializer;
import ru.nsu.fit.dib.projectdib.initapp.InputListener;
import ru.nsu.fit.dib.projectdib.initapp.PhysicsLoader;
import ru.nsu.fit.dib.projectdib.initapp.SettingsLoader;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ClientConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ServerConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerReceiverThread;

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
   * Инициализация мультиплейера.
   */
  @Override
  protected void onPreInit() {

  }

  /**
   * Инициализация игры.
   */
  @Override
  protected void initGame() {
    ServerSocket serverSocket;
    Map<Integer, Socket> clientSockets = new HashMap<>();
    try {
      serverSocket = new ServerSocket(8080);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      System.out.println("Подключение");
      new Thread(() -> {
        Socket socket = new Socket();
        ClientConfig.addClientSocket(socket);
        try {
          socket.connect(new InetSocketAddress("localhost", 8080));
          ClientConfig.addClientId(socket.getInputStream().read());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }).start();
      Socket client = serverSocket.accept();
      //отправляем клиенту его id
      client.getOutputStream().write(1);
      clientSockets.put(1, client);
      ClientConfig.init();
      new ServerReceiverThread(client).start();
      System.out.println("Подключено");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
/*    try {
      Socket client = serverSocket.accept();
      new ServerReceiverThread(client).start();
      //отправляем клиенту его id
      client.getOutputStream().write(2);
      clientSockets.put(2, client);
      System.out.println("Подключено");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }*/
    ServerConfig.addClientSockets(clientSockets);
    ServerConfig.init();
    GameInitializer gameInitializer = new GameInitializer();
    gameInitializer.run();
    inputListener.initialize(gameInitializer.getPlayer());
    player = gameInitializer.getPlayer();
  }
}
