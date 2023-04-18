package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSDeserializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;

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
    byte[] data = new byte[byteArray.length - 1];
    System.arraycopy(byteArray, 1, data, 0, data.length);
    ByteBuffer byteBuffer = ByteBuffer.allocate(data.length);
    byteBuffer.put(data).flip();
    switch (MessageType.getMessageType(byteArray[0])){
      case UPDATE -> {
        return new Pair<>(MessageType.UPDATE, FBSDeserializer.deserializeEntityStateList(byteBuffer));
      }
      case SPAWN -> {
        //System.out.println(Arrays.toString(byteArray));
        return new Pair<>(MessageType.SPAWN, FBSDeserializer.deserializeSpawnAction(byteBuffer));
      }
      default -> {
        throw new IllegalArgumentException("Illegal packet type");
      }
    }
  }
}
