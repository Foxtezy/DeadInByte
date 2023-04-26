package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public class ServerActionThread extends Thread {

  private BlockingQueue<Pair<MessageType, Object>> actionQueue = new LinkedBlockingQueue<>();

  private Integer nextEntityId = 10;

  public void addActionTask(Pair<MessageType, Object> gameAction) {
    actionQueue.add(gameAction);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      Pair<MessageType, Object> inPacket = null;
      try {
        inPacket = actionQueue.take();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      assert inPacket != null;
      Pair<MessageType, Object> outPacket = switch (inPacket.getKey()) {
        case SPAWN -> {
          SpawnAction spawnAction = (SpawnAction) inPacket.getValue();
          spawnAction.getNewEntity().setWeaponId(nextEntityId++);
          yield new Pair<>(MessageType.SPAWN, spawnAction);
        }
        default -> null;
      };
      Sender sender = new Sender();
      MCServer.getClientSockets().values().forEach(s -> sender.send(s, outPacket));
    }
  }
}
