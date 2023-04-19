package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public class ClientSenderThread extends Thread {

  private BlockingQueue<Pair<MessageType, Object>> actionQueue = new LinkedBlockingQueue<>();

  public void addActionTask(Pair<MessageType, Object> gameAction) {
    actionQueue.add(gameAction);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      List<EntityState> entityStates = MCClient.getClientState()
          .getEntityStatesByOwnerId(MCClient.getClientId());
      Utils.delay();
      // тут задержка должна быть
      Sender sender = new Sender();
      if (!entityStates.isEmpty()) {
        sender.send(MCClient.getClientSocket(), new Pair<>(MessageType.UPDATE, entityStates));
      }
      while (!actionQueue.isEmpty()){
        Pair<MessageType, Object> pair = actionQueue.poll();
        sender.send(MCClient.getClientSocket(),pair);
      }
    }
  }

}
