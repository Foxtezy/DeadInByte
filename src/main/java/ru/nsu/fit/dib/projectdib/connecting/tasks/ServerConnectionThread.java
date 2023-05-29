package ru.nsu.fit.dib.projectdib.connecting.tasks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.data.ProjectConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ServerConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerReceiverThread;

/**
 * Задание которое должно выполняться в CompletableFuture
 * Используется собственный interrupt для завершения
 */
public class ServerConnectionThread extends Thread {

  private int lastClientId = 1;

  private final Map<Integer, Socket> clientSockets = new ConcurrentHashMap<>();

  private boolean gameStarted = false;

  public Map<Integer, Socket> getClientSockets() {
    return clientSockets;
  }

  public void startGame() {
    this.gameStarted = true;
  }

  @Override
  public void run() {
    ServerSocket serverSocket;
    try {
      serverSocket = new ServerSocket(ProjectConfig.SERVER_PORT);
      serverSocket.setSoTimeout(100);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    CompletableFuture.supplyAsync(
        new ClientConnectionTask(new InetSocketAddress("localhost", ProjectConfig.SERVER_PORT)));
    ServerConfig.init();
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Socket client = serverSocket.accept();
        //отправляем клиенту его id
        client.getOutputStream().write(lastClientId);
        clientSockets.put(lastClientId, client);
        MCServer.getClientSockets().put(lastClientId++, client);
        ServerReceiverThread t = new ServerReceiverThread(client);
        t.setDaemon(true);
        t.start();
        MCServer.getReceiverThreads().add(t);
        if (gameStarted) {
          new Sender().send(client, new Pair<>(MessageType.START_GAME, null));
        }
      } catch (SocketTimeoutException e) {

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    try {
      serverSocket.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
