package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import java.io.IOException;
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
import ru.nsu.fit.dib.projectdib.initapp.SpawnEnemiesInit;
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
    new SpawnEnemiesInit().run(actionQueue);
    while (!Thread.currentThread().isInterrupted()) {
      Pair<MessageType, Object> inPacket = null;
      try {
        inPacket = actionQueue.take();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
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
          int newHP = attackedEntity.getComponent(HealthIntComponent.class).getValue()-hpAction.getAttackedHP();
          attackedEntity.getComponent(HealthIntComponent.class).setValue(newHP);
          yield new Pair<>(MessageType.HP,
              new HPAction(hpAction.getAttackingID(), hpAction.getAttackedID(),newHP));
        }
        case SPAWN -> {
          SpawnAction spawnAction = (SpawnAction) inPacket.getValue();
          if (Projectiles.getByName(spawnAction.getNewEntity().getEntityType()) != null) {
            System.out.println(spawnAction.getNewEntity().getEntityType());
            spawnAction.getNewEntity().getState().setID(nextEntityId++);
          } else {
            spawnAction.getNewEntity().setWeaponId(nextEntityId++);
          }
          yield new Pair<>(MessageType.SPAWN, spawnAction);
        }
        default -> null;
      };
      if (outPacket != null) {
        Sender sender = new Sender();
        MCServer.getClientSockets().values().forEach(s -> {
          try {
            sender.send(s, outPacket);
          } catch (IOException e) {

          }
        });
      }
    }
  }
}
