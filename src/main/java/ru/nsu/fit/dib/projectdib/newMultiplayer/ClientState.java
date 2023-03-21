package ru.nsu.fit.dib.projectdib.newMultiplayer;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.IDComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class ClientState {

  public Map<Integer, Entity> getIdHashTable() {
    return idHashTable;
  }

  //Хранятся все ID всех отслеживаемых Entity
  private final Map<Integer, Entity> idHashTable = new HashMap<>();

  public List<Entity> spawnEntities(List<NewEntity> newEntities) {
    if (newEntities.isEmpty()) {
      return new ArrayList<>();
    }
    List<Entity> entityList = new ArrayList<>();
    newEntities.forEach(newEntityData -> {
      Entity entity = spawn(newEntityData.getName(), newEntityData.getSpawnData());
      entity.addComponent(new IDComponent(newEntityData.getName(), newEntityData.getId()));
      idHashTable.put(newEntityData.getId(), entity);
      entityList.add(entity);
    });
    return entityList;
  }

  public void updateEntities(List<EntityState> entityStates) {
    entityStates.forEach(entityState -> {
      if (!idHashTable.containsKey(entityState.getId())) {
        // TODO: 14.03.2023 вызов метода отправки пакета ошибки
      }
      Entity entity = idHashTable.get(entityState.getId());
      Point2D currPos = entity.getPosition();
      Point2D newPos = entityState.getCoordinate();
      PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);
      entity.setPosition(new Vec2(newPos).add(currPos.multiply(-1)));
    });
  }

  public List<EntityState> getEntityStates() {
    return idHashTable.entrySet().stream()
        .map(e -> new EntityState(e.getKey(), e.getValue().getPosition(), null)).collect(
            Collectors.toList());
  }

  public Entity acceptedSpawn(NewEntity newEntity) {
    return MCClient.getClientThread().spawnNewEntity(newEntity);
  }

  /*public void getSpawnRequest(String name, SpawnData spawnData) {
    NewEntity spawnedNewEntity = new NewEntity(newId, name, spawnData);
    listNewEntities.add(spawnedNewEntity);
    EntityState spawnedEntityState = new EntityState(newId, spawned.getPosition(),
        spawned.getComponent(
            PlayerComponent.class).getMouseVelocity()); // TODO: 14.03.2023 Фигня
    listEntityState.add(spawnedEntityState);
    idServerHashTable.put(newId, spawned);
  } */
}
