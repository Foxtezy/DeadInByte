package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSSerializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketSizeException;

public class Sender {

  public void send(Socket address, Pair<MessageType, Object> message) {
    ByteBuffer buffer = switch (message.getKey()){
      case UPDATE -> FBSSerializer.serialize((SpawnAction) message.getValue());
      case SPAWN -> FBSSerializer.serialize((List<EntityState>) message.getValue());
      default -> throw new IllegalArgumentException("Illegal type of message");
    };
    byte[] p = buffer.array();
    try {
      address.getOutputStream().write(p.length);
      address.getOutputStream().write(p);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    //Передаем
    //...
  }

}
