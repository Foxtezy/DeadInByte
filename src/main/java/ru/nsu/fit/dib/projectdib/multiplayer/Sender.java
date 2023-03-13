package ru.nsu.fit.dib.projectdib.multiplayer;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import ru.nsu.fit.dib.projectdib.multiplayer.exeptions.PacketSizeException;
import ru.nsu.fit.dib.projectdib.multiplayer.packet.GameStatePacket;

public class Sender {

  private final DatagramSocket socket;

  private final SocketAddress address;

  public Sender(DatagramSocket socket, SocketAddress address) {
    this.socket = socket;
    this.address = address;
  }

  public void send(GameStatePacket gameStatePacket) {
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
}
