package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ClientConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public class ServerReceiverThread extends Thread {

  private final Receiver receiver;

  public ServerReceiverThread(Socket socket) {
    this.receiver = new Receiver(socket);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      var inPacket = receiver.receive();
      switch (inPacket.getKey()) {
        case UPDATE -> {
          MCServer.getUpdaterThread().addUpdateTask((List<EntityState>) inPacket.getValue());
        }
        case START_INIT -> {
          //отправка иниц пакетов
          Sender sender = new Sender();
          MCServer.getServerState().getSpawnActionList()
              .forEach(a -> sender.send(receiver.getSocket(), new Pair<>(MessageType.SPAWN, a)));
          sender.send(receiver.getSocket(), new Pair<>(MessageType.END_INIT, null));
        }
        case MAP_SEED -> {
          Sender sender = new Sender();
          sender.send(receiver.getSocket(), new Pair<>(MessageType.MAP_SEED, MCServer.getServerState().getMapSeed()));
        }
        default -> {
          MCServer.getActionThread().addActionTask(inPacket);
        }
      }
    }
  }
}
