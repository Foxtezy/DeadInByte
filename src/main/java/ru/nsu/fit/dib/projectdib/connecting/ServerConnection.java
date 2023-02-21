package ru.nsu.fit.dib.projectdib.connecting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class ServerConnection {

  private final DatagramSocket ds;

  public ServerConnection(DatagramSocket ds) {
    this.ds = ds;
  }

  /**
   * Подключение.
   *
   * @return адрес подключенного.
   */
  public SocketAddress accept(int timeout) throws SocketTimeoutException {
    byte[] hello = "hello".getBytes();
    DatagramPacket inPack = new DatagramPacket(new byte[5], 5);
    try {
      ds.setSoTimeout(timeout);
      ds.receive(inPack);
      DatagramPacket outPack = new DatagramPacket(hello, hello.length, inPack.getAddress(),
          inPack.getPort());
      ds.send(outPack);
      return new InetSocketAddress(inPack.getAddress(), inPack.getPort());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
