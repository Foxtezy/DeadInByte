package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import com.almasb.fxgl.entity.Entity;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction.WeaponActionType;
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
    //Для теста
    try {
      actionQueue.add(actionQueue.take());//Костыль для ожидания соощения от клиента
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    SpawnAction sa = new SpawnAction(new NewEntity(Weapons.Staff.getName(), 1,
        new EntityState(-1, new Point2D(8000, 8000), new Point2D(0, 0), 23)));
    SpawnAction sad = new SpawnAction(new NewEntity(EnemyType.Devil.getName(), 123,
        new EntityState(30, new Point2D(8000, 8000), new Point2D(0, 0), 24)));
    actionQueue.add(new Pair<>(MessageType.SPAWN, sa));
    actionQueue.add(new Pair<>(MessageType.SPAWN, sad));
    //Для теста
    while (!Thread.currentThread().isInterrupted()) {
      Pair<MessageType, Object> inPacket = null;
      try {
        inPacket = actionQueue.take();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      assert inPacket != null;
      Pair<MessageType, Object> outPacket = switch (inPacket.getKey()) {
        case WEAPON -> {
          WeaponAction weaponAction = (WeaponAction) inPacket.getValue();
          Entity weapon = MCClient.getClientState().getIdHashTable().get(weaponAction.getWeapon());
          if (weaponAction.getAction() == WeaponActionType.TAKE && weapon!=null && weapon.getComponent(WeaponComponent.class).hasUser()) {
            yield new Pair<>(MessageType.WEAPON, null);
          } else {
            yield new Pair<>(MessageType.WEAPON, weaponAction);
          }
        }
        case SPAWN -> {
          SpawnAction spawnAction = (SpawnAction) inPacket.getValue();
          spawnAction.getNewEntity().setWeaponId(nextEntityId++);
          yield new Pair<>(MessageType.SPAWN, spawnAction);
        }
        default -> null;
      };
      if (outPacket.getValue() != null) {
        Sender sender = new Sender();
        MCServer.getClientSockets().values().forEach(s -> sender.send(s, outPacket));
      }
    }
  }
}
