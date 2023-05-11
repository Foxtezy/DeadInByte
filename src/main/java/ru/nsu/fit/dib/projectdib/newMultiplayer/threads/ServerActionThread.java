package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.entity.creatures.TypeChooser;
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
/*    try {
      actionQueue.add(actionQueue.take());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    Point2D start = new Point2D(GameInitializer.lvl.start.getCentrePoint().x * 160,
        GameInitializer.lvl.start.getCentrePoint().y * 160);
    actionQueue.add(new Pair<>(MessageType.SPAWN,
        new SpawnAction(new NewEntity(EnemyType.Devil.getName(), 123, new EntityState(1231,
            start, new Point2D(0, 0), -1)))));*/
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
          Entity user = weapon.getComponent(WeaponComponent.class).getUser();
          if (weapon==null) yield null;
          if (weaponAction.getAction() == WeaponActionType.TAKE) {
            if (user!=null) {
              yield null;
            } else {
              yield new Pair<>(MessageType.WEAPON, weaponAction);
            }
          }
          else{
            if (weaponAction.getAction() == WeaponActionType.THROW){
              if (user!=null && user.getComponent(DataComponent.class).getId()==weaponAction.getUser()){
                yield new Pair<>(MessageType.WEAPON, weaponAction);
              }
              else {
                yield null;
              }
            }
          }
          yield null;
        }
        case HP -> {
          HPAction action = (HPAction) inPacket.getValue();
          Entity attackedEntity = MCClient.getClientState().getIdHashTable()
              .get(action.getAttackedID());
          if (attackedEntity == null) {
            yield null;
          }
          HPAction hpAction = (HPAction) inPacket.getValue();
          yield new Pair<>(MessageType.HP,
              new HPAction(hpAction.getAttackingID(), hpAction.getAttackedID(),
                  attackedEntity.getComponent(HealthIntComponent.class).getValue()));
        }
        case SPAWN -> {
          SpawnAction spawnAction = (SpawnAction) inPacket.getValue();
          if (Projectiles.getByName(spawnAction.getNewEntity().getEntityType()) != null) {
            spawnAction.getNewEntity().getState().setID(nextEntityId++);
          } else {
            spawnAction.getNewEntity().setWeaponId(nextEntityId++);
          }
          if (TypeChooser.getTypeByString(spawnAction.getNewEntity().getEntityType()) != EntityType.PROJECTILE) {
            MCServer.getServerState().addSpawnAction(spawnAction);
          }
          yield new Pair<>(MessageType.SPAWN, spawnAction);
        }
        default -> null;
      };
      if (outPacket != null) {
        Sender sender = new Sender();
        MCServer.getClientSockets().values().forEach(s -> sender.send(s, outPacket));
      }
    }
  }
}
