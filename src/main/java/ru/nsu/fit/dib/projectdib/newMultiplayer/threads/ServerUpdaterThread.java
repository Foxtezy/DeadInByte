package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public class ServerUpdaterThread extends Thread {

  private static final Integer SERVER_ID = -1;

  private BlockingQueue<Pair<MessageType, Object>> updaterQueue = new LinkedBlockingQueue<>();

  public void addUpdateTask(Pair<MessageType, Object> update) {
    updaterQueue.add(update);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      List<Pair<MessageType, Object>> updaterList = new ArrayList<>();
      updaterQueue.drainTo(updaterList);
      List<List<EntityState>> clientsStates = updaterList.stream().map(u -> (List<EntityState>)u.getValue()).collect(Collectors.toList());
      List<EntityState> outList = MCClient.getClientState().getEntityStatesByOwnerId(SERVER_ID);
      clientsStates.forEach(outList::addAll);
      Sender sender = new Sender();
      MCServer.getClientSockets().values().forEach(s -> sender.send(s, new Pair<>(MessageType.UPDATE, outList)));
    }
  }
}
