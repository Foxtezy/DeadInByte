package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSDeserializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketTypeException;

public class Receiver {

  private final Socket socket;

  public Receiver(Socket socket) {
    this.socket = socket;
  }

  public Pair<MessageType, Object> receive() throws PacketTypeException, SocketTimeoutException {
    try {
      socket.setSoTimeout(1000);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
    byte[] byteArray;
    //Получаем что то
    try {
      DataInputStream dis = new DataInputStream(socket.getInputStream());
      int len = dis.readInt();
      byteArray = new byte[len];
      dis.readFully(byteArray);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    //Передаем в буфер
    ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
    switch (MessageType.getMessageType(byteArray[0])){
      case UPDATE -> {
        return new Pair<>(MessageType.UPDATE, FBSDeserializer.deserializeEntityStateList(byteBuffer));
      }
      case SPAWN -> {
        return new Pair<>(MessageType.SPAWN, FBSDeserializer.deserializeSpawnAction(byteBuffer));
      }
      default -> {
        throw new IllegalArgumentException("Illegal packet type");
      }
    }
  }
}
