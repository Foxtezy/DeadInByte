package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import com.almasb.fxgl.entity.Entity;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionPacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionQueues;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketTypeException;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public class ClientThread extends Thread {

  private final SocketAddress serverAddress;

  private final Sender sender;

  private final Receiver receiver;

  private final ActionPacket actions = new ActionPacket(new HashMap<>(), new HashMap<>());
  private final ActionQueues actionQueues = new ActionQueues();
  private final Object monitor = new Object();

  public ClientThread(Receiver receiver, Sender sender, SocketAddress serverAddress) {
    this.serverAddress = serverAddress;
    this.sender = sender;
    this.receiver = receiver;
  }

  public Entity spawnNewEntity(SpawnAction newAction) {
    //newEntities.add(newEntity);
    while (true) {
      synchronized (monitor) {
        try {
          monitor.wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
      }
      //Сверяем по ID, которое в какой то момент времени изменится когда клиент получит пакет
      if (newAction.getNewEntity().getID()!=null) {
        Entity entity = MCClient.getClientState().getIdHashTable()
            .get(newAction.getNewEntity().getID());
        if (entity != null) {
          return entity;
        }
      }
    }
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      //List<NewEntity> newEntityList = new ArrayList<>();

      //Добавляем новые действия в мапу
      actionQueues.addToActionPacket(actions);
      //Отправляем пакет
      List<EntityState> entityStates = MCClient.getClientState().getEntityStates();
      sender.send(serverAddress, new GameStatePacket(actions, entityStates));
      try {
        //Обновляем состояния объектов
        GameStatePacket gameStatePacket = receiver.receive();
        MCClient.getClientState().updateEntities(gameStatePacket.getEntitiesStates());

        //Обновляем состояния действий
        //List<GameAction> actions = gameStatePacket.getActions();
        actions.update(gameStatePacket.getActions());
        // Как вариант - отдать MCClient-у мапу - пусть разбирается сам - выполняет APPROVED а потом меняет статус на COMPLETED

        //Запускаем в выполнение действия
        //  GameAction.do();
        MCClient.getClientState().doActions(actions);

        synchronized (monitor) {
          monitor.notifyAll();
        }
      } catch (PacketTypeException e) {
        throw new RuntimeException(e);
      } catch (SocketTimeoutException e) {
        throw new RuntimeException(e);
      }
    }
  }
}