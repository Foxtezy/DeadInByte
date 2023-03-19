package ru.nsu.fit.dib.projectdib;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import java.net.InetSocketAddress;
import java.util.List;
import ru.nsu.fit.dib.projectdib.initapp.GameInitializer;
import ru.nsu.fit.dib.projectdib.initapp.InputListener;
import ru.nsu.fit.dib.projectdib.initapp.PhysicsLoader;
import ru.nsu.fit.dib.projectdib.initapp.SettingsLoader;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ClientConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ServerConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerThread;

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

    ServerConfig.addClientAddresses(List.of(new InetSocketAddress("localhost", 9090)));
    ServerThread serverThread = new ServerThread(MCServer.getReceiver(), MCServer.getSender(), MCServer.getClientAddresses());
    ServerConfig.addServerThread(serverThread);

    ClientConfig.addServerAddress(new InetSocketAddress("localhost", 8080));
    ClientThread clientThread = new ClientThread(MCClient.getReceiver(), MCClient.getSender(), MCClient.getServerAddress());
    ClientConfig.addClientThread(clientThread);

    serverThread.start();
    clientThread.start();


    GameInitializer gameInitializer = new GameInitializer();
    gameInitializer.run();
    inputListener.initialize(gameInitializer.getPlayer());
    player=gameInitializer.getPlayer();
  }
}
