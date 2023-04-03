package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.entity.components.DataComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionPacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionStatus;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;

public class ClientState {

  public Map<Integer, Entity> getIdHashTable() {
    return idHashTable;
  }

  //Хранятся все ID всех отслеживаемых Entity
  private final Map<Integer, Entity> idHashTable = new ConcurrentHashMap<>();

  public List<Entity> spawnEntities(List<NewEntity> newEntities) {
    if (newEntities.isEmpty()) {
      return new ArrayList<>();
    }
    List<Entity> entityList = new ArrayList<>();
    for (int i = 0; i < newEntities.size(); i++) {
      Entity entity = newEntities.get(i).spawn();
      //TODO: заменить на SpawnAction
      entityList.add(entity);
    }
    return entityList;
  }

  public void updateEntities(List<EntityState> entityStates) {

    entityStates.forEach(entityState -> {
      if (!idHashTable.containsKey(entityState.getId())) {
        // TODO: 14.03.2023 вызов метода отправки пакета ошибки
      }
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      entityState.update();
    });
  }

  public List<EntityState> getEntityStates() {
    return idHashTable.entrySet().stream()
        .map(e -> new EntityState(e.getKey(),
            e.getValue().getComponent(DataComponent.class).getPosition(),
            e.getValue().getComponent(DataComponent.class).getRotation(),
            e.getValue().getComponent(DataComponent.class).getBindedEntity())).collect(
            Collectors.toList());
  }

  public Entity acceptedSpawn(NewEntity newEntity) {
    return MCClient.getClientThread().spawnNewEntity(new SpawnAction(newEntity));
  }
  public void acceptedAction(GameAction action) {
    MCClient.getClientThread().doAction(action);
  }
  public void doActions(ActionPacket actions) {
    actions.getSpawnActions().values().stream().filter(action -> action.getStatus() == ActionStatus.APPROVED).forEach(e -> {
      e.setStatus(ActionStatus.COMPLETED);
      e.run();
    });
    actions.getTakeWeaponActions().values().stream().filter(action -> action.getStatus() == ActionStatus.APPROVED).forEach(e -> {
      e.setStatus(ActionStatus.COMPLETED);
      e.run();
    });
  }
}
