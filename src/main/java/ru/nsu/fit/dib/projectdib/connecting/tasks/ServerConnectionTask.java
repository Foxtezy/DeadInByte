package ru.nsu.fit.dib.projectdib.connecting.tasks;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;
import ru.nsu.fit.dib.projectdib.connecting.ServerConnection;

/**
 * Задание которое должно выполняться в CompletableFuture
 * Используется собственный interrupt для завершения
 */
public class ServerConnectionTask implements Supplier<List<SocketAddress>> {

  private final DatagramSocket ds;

  private volatile boolean interrupt = false;

  private final BlockingQueue<SocketAddress> socketAddressQueue = new LinkedBlockingQueue<>();

  public ServerConnectionTask(DatagramSocket ds) {
    this.ds = ds;
  }

  public void interrupt() {
    this.interrupt = true;
  }

  public BlockingQueue<SocketAddress> getSocketAddressQueue() {
    return socketAddressQueue;
  }

  @Override
  public List<SocketAddress> get() {
    ServerConnection serverConnection = new ServerConnection(ds);
    byte id = 1;
    while (!interrupt) {
      try {
        socketAddressQueue.add(serverConnection.accept(id, 1000));
        id++;
      } catch (SocketTimeoutException ignored) {}
    }
    List<SocketAddress> socketAddressList = new ArrayList<>();
    socketAddressQueue.drainTo(socketAddressList);
    return socketAddressList;
  }
}
