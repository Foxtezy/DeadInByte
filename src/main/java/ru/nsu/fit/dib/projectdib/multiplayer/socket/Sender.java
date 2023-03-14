package ru.nsu.fit.dib.projectdib.multiplayer.socket;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import ru.nsu.fit.dib.projectdib.multiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.multiplayer.exeptions.PacketSizeException;

public class Sender {

  private final DatagramSocket socket;

  public Sender(DatagramSocket socket) {
    this.socket = socket;
  }

  public void send(SocketAddress address, GameStatePacket gameStatePacket) {
    StringWriter writer = new StringWriter();
    new Gson().toJson(gameStatePacket, writer);
    byte[] byteArray = writer.toString().getBytes(StandardCharsets.UTF_8);
    byte[] p = new byte[byteArray.length + 1];
    if (p.length > 55000) {
      throw new PacketSizeException();
    }
    p[0] = 1;
    System.arraycopy(byteArray, 0, p, 1, byteArray.length);
    DatagramPacket packet = new DatagramPacket(p, p.length, address);
    try {
      socket.send(packet);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void sendException(SocketAddress address, GameStatePacket gameStatePacket) {
    StringWriter writer = new StringWriter();
    new Gson().toJson(gameStatePacket, writer);
    byte[] byteArray = writer.toString().getBytes(StandardCharsets.UTF_8);
    byte[] p = new byte[byteArray.length + 1];
    if (p.length > 55000) {
      throw new PacketSizeException();
    }
    p[0] = 0;
    System.arraycopy(byteArray, 0, p, 1, byteArray.length);
    DatagramPacket packet = new DatagramPacket(p, p.length, address);
    try {
      socket.send(packet);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
