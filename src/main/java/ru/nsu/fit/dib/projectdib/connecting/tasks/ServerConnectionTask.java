package ru.nsu.fit.dib.projectdib.connecting.tasks;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import ru.nsu.fit.dib.projectdib.connecting.ServerConnection;

/**
 * Задание которое должно выполняться в CompletableFuture
 * Используется собственный interrupt для завершения
 */
public class ServerConnectionTask implements Supplier<List<SocketAddress>> {

  private final DatagramSocket ds;

  private volatile boolean interrupt = false;

  private final List<SocketAddress> socketAddressList = new ArrayList<>();

  public ServerConnectionTask(DatagramSocket ds) {
    this.ds = ds;
  }

  public void interrupt() {
    this.interrupt = true;
  }

  @Override
  public List<SocketAddress> get() {
    ServerConnection serverConnection = new ServerConnection(ds);
    while (!interrupt) {
      try {
        socketAddressList.add(serverConnection.accept(1000));
      } catch (SocketTimeoutException ignored) {}
    }
    return socketAddressList;
  }
}
