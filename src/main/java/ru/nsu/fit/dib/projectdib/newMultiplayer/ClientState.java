package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.entity.components.DataComponent;
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
      if (MCClient.getClientState().getIdHashTable().get(entityState.getId()) != null) {
        if (!MCClient.getClientState().getIdHashTable().get(entityState.getId()).getComponent(
            DataComponent.class).isClientEntity()) {
          entityState.update();
        }
      }
    });
  }

  public List<EntityState> getEntityStates() {
    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return idHashTable.entrySet().stream().filter(e->e.getValue().hasComponent(DataComponent.class))
        .map(e -> new EntityState(e.getKey(),
            e.getValue().getComponent(DataComponent.class).getPosition(),
            e.getValue().getComponent(DataComponent.class).getRotation(),
            e.getValue().getComponent(DataComponent.class).getBindedEntity())).collect(
            Collectors.toList());
  }

  public Entity acceptedSpawn(NewEntity newEntity) {
    acceptedAction(new SpawnAction(newEntity));

    // TODO: 16.04.2023 вот тут вопрос с таблицей
  }

  public void acceptedAction(GameAction action) {
    MCClient.getClientSenderThread().addActionTask(action);
  }

}
