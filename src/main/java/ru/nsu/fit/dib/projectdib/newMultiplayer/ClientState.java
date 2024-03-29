package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.entity.Entity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.Utils;

public class ClientState {

  public Map<Integer, Entity> getIdHashTable() {
    return idHashTable;
  }

  //Хранятся все ID всех отслеживаемых Entity
  private final Map<Integer, Entity> idHashTable = new ConcurrentHashMap<>();

  public void updateEntities(List<EntityState> entityStates) {
    Utils.delay();
    entityStates.forEach(entityState -> {
      if (!idHashTable.containsKey(entityState.getId())) {
        return;
      }
      if (!MCClient.getClientState().getIdHashTable().get(entityState.getId()).hasComponent(DataComponent.class)){
        System.out.println("someone");
        return;
      }
      if (MCClient.getClientState().getIdHashTable().get(entityState.getId()).getComponent(
          DataComponent.class).isClientEntity()) {
        return;
      }
      entityState.update();
    });
  }

  public List<EntityState> getEntityStatesByOwnerId(Integer id) {
    return idHashTable.entrySet().stream()
        .filter(e -> e.getValue().getType() == EntityType.PLAYER || e.getValue().getType() == EntityType.ENEMY)
        .filter(e -> e.getValue().hasComponent(DataComponent.class))
        .filter(e -> e.getValue().getComponent(DataComponent.class).getOwnerID() == id)
        .filter(e -> e.getValue().getComponent(DataComponent.class).isValid())
        .map(e -> new EntityState(e.getKey(),
            e.getValue().getComponent(DataComponent.class).getPosition(),
            e.getValue().getComponent(DataComponent.class).getRotation(),
            e.getValue().getComponent(DataComponent.class).getBindedEntity())
        ).collect(Collectors.toList());
  }

  public Optional<EntityState> getEntityState(Integer key) {
    List<Entity> entityList = List.of(idHashTable.get(key));
    return entityList.stream().filter(e -> e.hasComponent(DataComponent.class))
        .map(e -> new EntityState(key,
            e.getComponent(DataComponent.class).getPosition(),
            e.getComponent(DataComponent.class).getRotation(),
            e.getComponent(DataComponent.class).getBindedEntity())).findAny();

  }

  public Optional<SpawnAction> getSpawnAction(Integer key) {
    Entity entity = idHashTable.get(key);
    if (entity.hasComponent(DataComponent.class)) {
      System.out.println(key + ": valid");
      return Optional.of(new SpawnAction(new NewEntity(entity.getComponent(DataComponent.class).getType(), entity.getComponent(DataComponent.class).getSeed(), new EntityState(key,
          entity.getComponent(DataComponent.class).getPosition(),
          entity.getComponent(DataComponent.class).getRotation(),
          entity.getComponent(DataComponent.class).getBindedEntity()))));
    }
    System.out.println(key + ": INVALID");
    return Optional.empty();
/*    return entityList.stream().filter(e -> e.hasComponent(DataComponent.class)).filter(e -> e.getComponent(DataComponent.class).isValid())
        .map(e -> new SpawnAction(new NewEntity(e.getComponent(DataComponent.class).getType(), e.getComponent(DataComponent.class).getSeed(), new EntityState(key,
            e.getComponent(DataComponent.class).getPosition(),
            e.getComponent(DataComponent.class).getRotation(),
            e.getComponent(DataComponent.class).getBindedEntity())))).findAny();*/

  }

  public Entity acceptedSpawn(NewEntity newEntity) {
    acceptedAction(new Pair<>(MessageType.SPAWN, new SpawnAction(newEntity)));
    Entity player = null;
    // TODO: 19.04.2023 Observer pattern
    while (player == null) {
      player = idHashTable.get(newEntity.getID());
    }
    return player;
  }

  public void acceptedAction(Pair<MessageType, Object> action) {
    MCClient.getClientSenderThread().addActionTask(action);
  }
}
