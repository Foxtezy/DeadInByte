package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.entity.Entity;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.entity.components.DataComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;

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

  public List<EntityState> getEntityStatesByOwnerId(Integer id) {
    List<EntityState> list = idHashTable.entrySet().stream().filter(e->e.getValue().hasComponent(DataComponent.class))
        .filter(e -> e.getValue().getComponent(DataComponent.class).getOwnerID() == id)
        .filter(e ->e.getValue().getComponent(DataComponent.class).isValid())
        .map(e -> new EntityState(e.getKey(),
            e.getValue().getComponent(DataComponent.class).getPosition(),
            e.getValue().getComponent(DataComponent.class).getRotation(),
            e.getValue().getComponent(DataComponent.class).getBindedEntity())
        ).collect(Collectors.toList());
    // TODO: 18.04.2023 разобраться почему Vector при 1 значении падает
    if (list.size()==1) list.add(list.get(0));
    return list;
  }

  public Entity acceptedSpawn(NewEntity newEntity) {
    acceptedAction(new Pair<>(MessageType.SPAWN, new SpawnAction(newEntity)));
    Entity player=null;
    while (player==null){
      player = idHashTable.get(newEntity.getID());
    }
    return player;
  }

  public void acceptedAction(Pair<MessageType, Object> action) {
    MCClient.getClientSenderThread().addActionTask(action);
  }
}
