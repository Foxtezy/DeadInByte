package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.ContextException;
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
      Utils.delay();
      List<EntityState> outList;
      try {
        outList = MCClient.getClientState().getEntityStatesByOwnerId(SERVER_ID);
      } catch (ContextException e) {
        continue;
      }
      // TODO: 19.04.2023 Сделать нормальную проверку на наличие данных для передачи
      //  (пока что сервер все складывает в outList и проверяет на 0)
      List<List<EntityState>> clientsStates = new ArrayList<>();
      updaterQueue.drainTo(clientsStates);
      clientsStates.forEach(outList::addAll);
      Sender sender = new Sender();
      List<EntityState> finalOutList = outList;
      if (finalOutList.isEmpty()) continue;
      MCServer.getClientSockets().values()
          .forEach(s -> {
            try {
              sender.send(s, new Pair<>(MessageType.UPDATE, finalOutList));
            } catch (IOException e) {
            }
          });
    }
  }
}
