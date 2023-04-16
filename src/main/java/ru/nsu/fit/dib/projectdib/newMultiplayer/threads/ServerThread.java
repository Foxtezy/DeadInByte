package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketTypeException;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public class ServerThread extends Thread {


  private final Receiver receiver;

  private final Sender sender;

  private final List<Socket> clientSockets;

  private Integer nextEntityId = 10;

  public ServerThread(Receiver receiver, Sender sender, List<Socket> clientSockets) {
    this.receiver = receiver;
    this.sender = sender;
    this.clientSockets = clientSockets;
  }


  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      Pair<MessageType, Object> inPacket;
      try {
        inPacket = receiver.receive();
      } catch (SocketTimeoutException e) {
        // сеть упала
        throw new RuntimeException("сеть упала=(" + e);
      }


      Pair<MessageType, Object> outPacket = switch (inPacket.getKey()) {
        case SPAWN -> {
          SpawnAction spawnAction = (SpawnAction) inPacket.getValue();
          spawnAction.getNewEntity().setWeaponId(nextEntityId++);
          yield new Pair<>(MessageType.SPAWN, spawnAction);
        }
        case UPDATE -> new Pair<>(MessageType.UPDATE, inPacket.getValue());
        default -> {
          // TODO: 16.04.2023 оствльные
          yield null;
        }
      };
      clientSockets.forEach(s -> sender.send(s, outPacket));
    }
  }
}
