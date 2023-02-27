package ru.nsu.fit.dib.projectdib.connecting.runners;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.fit.dib.projectdib.connecting.tasks.ServerConnectionTask;

/**
 * Класс который запускает поток принимающий соединенияж
 */
public class ServerConnectionRunner {

  private final List<SocketAddress> clientSockets = new ArrayList<>();

  private final DatagramSocket serverSocket;

  private Thread connectionThread;

  public ServerConnectionRunner(DatagramSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public void startConnection() {
    connectionThread = new Thread(new ServerConnectionTask(serverSocket, clientSockets));
    connectionThread.start();
  }

  public List<SocketAddress> stopConnection() {
    connectionThread.interrupt();
    try {
      connectionThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return clientSockets;
  }

}
