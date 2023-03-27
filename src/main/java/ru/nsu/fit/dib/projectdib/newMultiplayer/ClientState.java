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
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

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
      if (Objects.equals(newEntities.get(i).getName(), "weapon")) {
        Entity entity = Factory.spawnWeapon(Weapons.valueOf(newEntities.get(i).getEntityType()), newEntities.get(i).getPosition());
        idHashTable.put(newEntities.get(i).getId(), entity);
        entityList.add(entity);
      }
    }
    return entityList;
  }

  public void updateEntities(List<EntityState> entityStates) {
    entityStates.forEach(entityState -> {
      if (!idHashTable.containsKey(entityState.getId())) {
        // TODO: 14.03.2023 вызов метода отправки пакета ошибки
      }
      Entity entity = idHashTable.get(entityState.getId());
      Point2D currPos = entity.getPosition();
      Point2D newPos = entityState.getPosition();
      entity.setPosition(new Vec2(newPos));
    });
  }

  public List<EntityState> getEntityStates() {
    return idHashTable.entrySet().stream()
        .map(e -> new EntityState(e.getKey(), e.getValue().getPosition(), null, null)).collect(
            Collectors.toList());
  }

  public Entity acceptedSpawn(NewEntity newEntity) {
    return MCClient.getClientThread().spawnNewEntity(newEntity);
  }
}
