package ru.nsu.fit.dib.projectdib.multiplayer;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.IDComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.multiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.multiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.multiplayer.data.NewEntity;

public class Client {

  public Map<Integer, Entity> getIdHashTable() {
    return idHashTable;
  }

  //Хранятся все ID всех отслеживаемых Entity
  private final Map<Integer, Entity> idHashTable = new HashMap<>();

  public void processPacket(GameStatePacket newPacket) {
    spawnEntities(newPacket.getNewEntityList());
    updateEntities(newPacket.getAllCoordinatesOfEntitiesList());
  }

  public void spawnEntities(List<NewEntity> newEntities) {
    if (newEntities == null) {
      return;
    }
    newEntities.forEach(newEntityData -> {
      Entity entity = spawn(newEntityData.getName(), newEntityData.getSpawnData());
      entity.addComponent(new IDComponent(newEntityData.getName(), newEntityData.getId()));
      idHashTable.put(newEntityData.getId(), entity);
    });
  }

  public void updateEntities(List<EntityState> entityStates) {
    entityStates.forEach(entityState -> {
      if (!idHashTable.containsKey(entityState.getId())) {
        // TODO: 14.03.2023 вызов метода отправки пакета ошибки
      }
      Entity entity = idHashTable.get(entityState.getId());
      Point2D currPos = entity.getPosition();
      Point2D newPos = entity.getPosition();
      PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);
      entity.setPosition(new Vec2(newPos).add(currPos.multiply(-1)));
    });
  }
}
