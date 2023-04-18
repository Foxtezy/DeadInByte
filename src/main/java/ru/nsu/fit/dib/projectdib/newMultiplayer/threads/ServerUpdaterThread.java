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

  private BlockingQueue<List<EntityState>> updaterQueue = new LinkedBlockingQueue<>();

  public void addUpdateTask(List<EntityState> list) {
    updaterQueue.add(list);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      List<EntityState> outList = MCClient.getClientState().getEntityStatesByOwnerId(SERVER_ID);
      while (outList.size()==0){
        outList=MCClient.getClientState().getEntityStatesByOwnerId(SERVER_ID);
        List<List<EntityState>> clientsStates = new ArrayList<>();
        updaterQueue.drainTo(clientsStates);
        clientsStates.forEach(outList::addAll);
      }
      Sender sender = new Sender();
      System.out.println(outList);
      List<EntityState> finalOutList = outList;
      MCServer.getClientSockets().values()
          .forEach(s -> sender.send(s, new Pair<>(MessageType.UPDATE, finalOutList)));
    }
  }
}
