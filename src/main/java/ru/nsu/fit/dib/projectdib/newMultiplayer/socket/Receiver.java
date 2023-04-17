package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSDeserializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketTypeException;

public class Receiver {

  private final Socket socket;

  public Receiver(Socket socket) {
    this.socket = socket;
  }

  public Pair<MessageType, Object> receive() {
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
