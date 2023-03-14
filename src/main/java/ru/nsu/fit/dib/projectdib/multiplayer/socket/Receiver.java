package ru.nsu.fit.dib.projectdib.multiplayer.socket;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import ru.nsu.fit.dib.projectdib.multiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.multiplayer.exeptions.PacketTypeException;

public class Receiver {

  private final DatagramSocket socket;

  public Receiver(DatagramSocket socket) {
    this.socket = socket;
  }

  public GameStatePacket receive() throws PacketTypeException, SocketTimeoutException {
    try {
      socket.setSoTimeout(1000);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
    byte[] byteArray = new byte[60000];
    DatagramPacket p = new DatagramPacket(byteArray, byteArray.length);
    try {
      socket.receive(p);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    if (p.getData()[0] != 1) {
      String json = new String(p.getData(), 1, p.getLength(), StandardCharsets.UTF_8);
      throw new PacketTypeException(new Gson().fromJson(json, GameStatePacket.class));
    }
    String json = new String(p.getData(), 1, p.getLength(), StandardCharsets.UTF_8);
    return new Gson().fromJson(json, GameStatePacket.class);
  }
}
