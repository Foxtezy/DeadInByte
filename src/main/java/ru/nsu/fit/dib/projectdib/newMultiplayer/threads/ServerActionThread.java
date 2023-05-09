package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import com.almasb.fxgl.entity.Entity;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.initapp.GameInitializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.HPAction;
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
    try {
      actionQueue.add(actionQueue.take());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    Point2D start = new Point2D(GameInitializer.lvl.start.getCentrePoint().x*160,GameInitializer.lvl.start.getCentrePoint().y*160);
    actionQueue.add(new Pair<>(MessageType.SPAWN,new SpawnAction(new NewEntity(EnemyType.Devil.getName(), 123, new EntityState(1231,
        start,new Point2D(0,0),-1)))));
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
        case HP -> {
          HPAction action = (HPAction) inPacket.getValue();
          if (MCClient.getClientState().getIdHashTable().get(action.getAttackedID())==null) yield null;
          yield inPacket;
        }
        case SPAWN -> {
          SpawnAction spawnAction = (SpawnAction) inPacket.getValue();
          if (Projectiles.getByName(spawnAction.getNewEntity().getEntityType())!=null){
            spawnAction.getNewEntity().getState().setID(nextEntityId++);
          }
          else {
            spawnAction.getNewEntity().setWeaponId(nextEntityId++);
          }
          MCServer.getServerState().addSpawnAction(spawnAction);
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
