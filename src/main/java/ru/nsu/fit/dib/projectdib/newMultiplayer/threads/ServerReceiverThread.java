package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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
      Pair<MessageType, Object> inPacket = null;
      try {
        inPacket = receiver.receive();
      } catch (Exception e) {
        break;
      }
      switch (inPacket.getKey()) {
        case UPDATE -> {
          MCServer.getUpdaterThread().addUpdateTask((List<EntityState>) inPacket.getValue());
        }
        case START_INIT -> {
          //отправка иниц пакетов
          Sender sender = new Sender();
          MCServer.getServerState().getInitSpawnList()
              .forEach(a -> {
                try {
                  sender.send(receiver.getSocket(), new Pair<>(MessageType.SPAWN, a));
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              });
          MCServer.getServerState().getInitHPactionList()
              .forEach(a -> {
                try {
                  sender.send(receiver.getSocket(), new Pair<>(MessageType.HP, a));
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              });
          MCServer.getServerState().getInitWeaponActionList()
              .forEach(a -> {
                try {
                  sender.send(receiver.getSocket(), new Pair<>(MessageType.WEAPON, a));
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              });
          try {
            sender.send(receiver.getSocket(), new Pair<>(MessageType.END_INIT, null));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
        case MAP_SEED -> {
          Sender sender = new Sender();
          try {
            sender.send(receiver.getSocket(), new Pair<>(MessageType.MAP_SEED, MCServer.getServerState().getMapSeed()));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
        case ERROR -> {
          continue;
        }
        default -> {
          MCServer.getActionThread().addActionTask(inPacket);
        }
      }
    }
  }
}
