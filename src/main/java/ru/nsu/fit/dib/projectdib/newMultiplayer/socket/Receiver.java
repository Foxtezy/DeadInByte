package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSDeserializer;

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
      System.out.println(len);
      dis.readFully(byteArray);
      dis.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    //Передаем в буфер
    byte[] message = new byte[byteArray.length - 1];
    System.arraycopy(byteArray, 1, message, 0, message.length);
    ByteBuffer byteBuffer = ByteBuffer.wrap(message);
    switch (MessageType.getMessageType(byteArray[0])){
      case UPDATE -> {
        return new Pair<>(MessageType.UPDATE, FBSDeserializer.deserializeEntityStateList(byteBuffer));
      }
      case SPAWN -> {
        System.out.println(Arrays.toString(byteArray));
        return new Pair<>(MessageType.SPAWN, FBSDeserializer.deserializeSpawnAction(byteBuffer));
      }
      default -> {
        throw new IllegalArgumentException("Illegal packet type");
      }
    }
  }
}
