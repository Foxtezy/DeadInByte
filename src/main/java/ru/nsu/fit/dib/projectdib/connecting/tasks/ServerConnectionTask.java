package ru.nsu.fit.dib.projectdib.connecting.tasks;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import ru.nsu.fit.dib.projectdib.connecting.ServerConnection;

public class ServerConnectionTask implements Runnable {

  private final DatagramSocket ds;

  private final List<SocketAddress> socketAddressList;

  public ServerConnectionTask(DatagramSocket ds, List<SocketAddress> socketAddressList) {
    this.ds = ds;
    this.socketAddressList = socketAddressList;
  }

  @Override
  public void run() {
    ServerConnection serverConnection = new ServerConnection(ds);
    while (!Thread.interrupted()) {
      try {
        socketAddressList.add(serverConnection.accept(1000));
      } catch (SocketTimeoutException ignored) {}
    }
  }
}
