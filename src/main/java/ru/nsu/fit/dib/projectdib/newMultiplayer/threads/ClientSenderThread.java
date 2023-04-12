package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;

public class ClientSenderThread extends Thread {

  private BlockingQueue<GameAction> actionQueue = new LinkedBlockingQueue<>();

  public void addActionTask(GameAction gameAction) {
    actionQueue.add(gameAction);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      List<EntityState> entityStates = MCClient.getClientState().getEntityStates();
      //тут отправляем на сервер список с состояниями
      //actionQueue.forEach(отправка); отправка actions
    }
  }

}
