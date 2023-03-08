package ru.nsu.fit.dib.projectdib.connecting;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Информация о подключении.
 */
public class ConnectionInfo {

  private final DatagramSocket datagramSocket;

  {
    try {
      datagramSocket = new DatagramSocket(8080);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
  }

  public DatagramSocket getDatagramSocket() {
    return datagramSocket;
  }

}
