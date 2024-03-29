package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSSerializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.HPAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction;

public class Sender {

  public void send(Socket address, Pair<MessageType, Object> message) throws IOException{
    ByteBuffer buffer = switch (message.getKey()) {
      case HP -> FBSSerializer.serialize((HPAction) message.getValue());
      case WEAPON -> FBSSerializer.serialize((WeaponAction) message.getValue());
      case SPAWN -> FBSSerializer.serialize((SpawnAction) message.getValue());
      case UPDATE -> FBSSerializer.serialize((List<EntityState>) message.getValue());
      case START_GAME, START_INIT, END_INIT -> ByteBuffer.wrap(new byte[]{});
      case MAP_SEED -> ByteBuffer.allocate(4).putInt((int) message.getValue());
      default -> {
        System.out.println(message.getKey());
        throw new IllegalArgumentException("Illegal type of message");
      }
    };

    //System.out.println(message.getKey().toString()+" sended");
    buffer.compact().position(0);
    byte[] data = new byte[buffer.remaining()];
    buffer.get(data);
    byte[] pt = new byte[data.length + 1];
    System.arraycopy(data, 0, pt, 1, data.length);
    pt[0] = message.getKey().getId();
    synchronized (address) {
      DataOutputStream d = new DataOutputStream(address.getOutputStream());
      d.writeInt(pt.length);
      address.getOutputStream().write(pt);
    }
  }
}
