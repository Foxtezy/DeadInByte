package ru.nsu.fit.dib.projectdib.connecting.tasks;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.function.Supplier;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ClientConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class ClientConnectionTask implements Supplier<Socket> {

  private final SocketAddress serverAddress;

  public ClientConnectionTask(SocketAddress serverAddress) {
    this.serverAddress = serverAddress;
  }

  /**
   * Выкидывает SocketTimeoutException при неправильном адресе
   */
  @Override
  public Socket get() {
    ClientConfig.init();
    Socket socket = new Socket();
    ClientConfig.addClientSocket(socket);
    try {
      socket.connect(serverAddress, 1000);
      ClientConfig.addClientId(socket.getInputStream().read());
    } catch (Exception e) {
      return null;
    }
    return socket;
  }
}
