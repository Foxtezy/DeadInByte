package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import com.almasb.fxgl.entity.Entity;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionPacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionQueues;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionStatus;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
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

  private final ActionPacket actions = new ActionPacket(Collections.synchronizedMap(new HashMap<>()),
      Collections.synchronizedMap(new HashMap<>())); // TODO: 03.04.2023 удалять actions по завершении
  private final ActionQueues actionQueues = new ActionQueues();
  private final Object monitor = new Object();

  public ClientThread(Receiver receiver, Sender sender, SocketAddress serverAddress) {
    this.serverAddress = serverAddress;
    this.sender = sender;
    this.receiver = receiver;
  }

  public Entity spawnNewEntity(SpawnAction newAction) {
    actionQueues.getNewSpawnActions().add(newAction);
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
      if (newAction.getNewEntity().getID() != null && MCClient.getClientState().getIdHashTable()
          .get(newAction.getNewEntity().getID()) != null) {
        return MCClient.getClientState().getIdHashTable()
            .get(newAction.getNewEntity().getID());
      }
    }
  }

  public void doAction(GameAction newAction) {
    actionQueues.addAction(newAction);
    /*
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
      if (newAction.getStatus() == ActionStatus.COMPLETED) {
        return;
      }
    }*/
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      //System.out.println("spawn: "+actions.getSpawnActions().keySet());
      //System.out.println("takeWeapon: "+actions.getTakeWeaponActions().keySet());
      //Добавляем новые действия в мапу
      actionQueues.addToActionPacket(actions);
      //Отправляем пакет
      List<EntityState> entityStates = MCClient.getClientState().getEntityStates();
      //System.out.println(entityStates);
      try {
        //Обновляем состояния объектов
        GameStatePacket gameStatePacket = receiver.receive();
        gameStatePacket.getEntitiesStates().stream().filter(e -> e.getId() == 2).forEach(e -> System.out.println(e));
        MCClient.getClientState().updateEntities(gameStatePacket.getEntitiesStates());

        //Обновляем состояния действий
        actions.update(gameStatePacket.getActions());
        // Как вариант - отдать MCClient-у мапу - пусть разбирается сам - выполняет APPROVED а потом меняет статус на COMPLETED

        //Запускаем в выполнение действия
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
