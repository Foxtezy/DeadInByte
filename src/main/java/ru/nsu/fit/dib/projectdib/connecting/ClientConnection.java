package ru.nsu.fit.dib.projectdib.connecting;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class ClientConnection {

  private final InetAddress host;

  private final int port;

  public ClientConnection(InetAddress host, int port) {
    this.host = host;
    this.port = port;
  }

  /**
   * При вызове лучше ловить SocketTimeoutException
   *
   * @throws SocketTimeoutException ошибка
   */
  public void connect(int timeout) throws SocketTimeoutException {
    byte[] hello = "hello".getBytes();
    DatagramPacket outPack = new DatagramPacket(hello, hello.length, host, port);
    DatagramPacket inPack = new DatagramPacket(new byte[5], 5);
    try (DatagramSocket ds = new DatagramSocket()) {
      System.out.println(ds.getInetAddress());
      ds.setSoTimeout(timeout);
      ds.send(outPack);
      ds.receive(inPack);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
