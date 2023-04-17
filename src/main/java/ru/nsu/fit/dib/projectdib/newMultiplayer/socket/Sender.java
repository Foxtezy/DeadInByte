package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSSerializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;

public class Sender {

  public void send(Socket address, Pair<MessageType, Object> message) {
    ByteBuffer buffer = switch (message.getKey()){
      case UPDATE -> FBSSerializer.serialize((SpawnAction) message.getValue());
      case SPAWN -> FBSSerializer.serialize((List<EntityState>) message.getValue());
      default -> throw new IllegalArgumentException("Illegal type of message");
    };
    byte[] p = buffer.array();
    try {
      synchronized (address) {
        address.getOutputStream().write(p.length);
        address.getOutputStream().write(p);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
