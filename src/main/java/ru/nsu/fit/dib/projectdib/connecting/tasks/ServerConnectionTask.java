package ru.nsu.fit.dib.projectdib.connecting.tasks;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ServerConfig;

/**
 * Задание которое должно выполняться в CompletableFuture
 * Используется собственный interrupt для завершения
 */
public class ServerConnectionTask implements Supplier<Map<Integer, Socket>> {

  private volatile boolean interrupt = false;

  private int lastClientId = 1;

  public void interrupt() {
    this.interrupt = true;
  }

  @Override
  public Map<Integer, Socket> get() {
    ServerSocket serverSocket;
    Map<Integer, Socket> clientSockets = new HashMap<>();
    try {
      serverSocket = new ServerSocket(8080);
      serverSocket.setSoTimeout(100);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    while (!interrupt) {
      try {
        Socket client = serverSocket.accept();
        //отправляем клиенту его id
        client.getOutputStream().write(lastClientId);
        clientSockets.put(lastClientId++, client);
      } catch (SocketTimeoutException e) {

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    ServerConfig.init();
    ServerConfig.addClientAddresses(clientSockets);
    return clientSockets;
  }
}
