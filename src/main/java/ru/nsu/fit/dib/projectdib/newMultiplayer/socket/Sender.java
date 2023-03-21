package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketSizeException;

public class Sender {

  private final DatagramSocket socket;

  private final Gson gson;

  public Sender(DatagramSocket socket, Gson gson) {
    this.socket = socket;
    this.gson = gson;
  }

  public void send(SocketAddress address, GameStatePacket gameStatePacket) {
    StringWriter writer = new StringWriter();
    new Gson().toJson(gameStatePacket, GameStatePacket.class, writer);
    byte[] byteArray = writer.toString().getBytes(StandardCharsets.UTF_8);
    byte[] p = new byte[byteArray.length + 1];
    if (p.length > 55000) {
      throw new PacketSizeException();
    }
    p[0] = MessageType.NEW_STATE.getId();
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
    gson.toJson(gameStatePacket, GameStatePacket.class, writer);
    byte[] byteArray = writer.toString().getBytes(StandardCharsets.UTF_8);
    byte[] p = new byte[byteArray.length + 1];
    if (p.length > 55000) {
      throw new PacketSizeException();
    }
    p[0] = MessageType.ERROR.getId();
    System.arraycopy(byteArray, 0, p, 1, byteArray.length);
    DatagramPacket packet = new DatagramPacket(p, p.length, address);
    try {
      socket.send(packet);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
