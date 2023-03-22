package ru.nsu.fit.dib.projectdib.multiplayer;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.IDComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Point2D;
import javax.swing.Action;
import ru.nsu.fit.dib.projectdib.data.json.update.UpdatedCreatureJSON;
import ru.nsu.fit.dib.projectdib.data.json.update.UpdatedWeaponJSON;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.multiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.multiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.multiplayer.data.NewEntity;

public class ClientTaskManager {

  public HashMap<Integer, Entity> getIdHashTable() {
    return idHashTable;
  }

  //Хранятся все ID всех отслеживаемых Entity
  public static HashMap<Integer,Entity> idHashTable;
  public ClientTaskManager(){
    idHashTable=new HashMap<>();
  }
  public void processPacket(GameStatePacket newPacket){
    spawnEntities(newPacket.getNewEntityList());
    updateEntities(newPacket.getAllCoordinatesOfEntitiesList());
  }
  public void spawnEntities(List<NewEntity> newEntities){
    newEntities.forEach(newEntityData->{
      Entity entity = spawn(newEntityData.getName(), newEntityData.getSpawnData());
      entity.addComponent(new IDComponent(newEntityData.getName(),newEntityData.getId()));
      idHashTable.put(newEntityData.getId(),entity);
  });
  }
  public void updateEntities(List<EntityState> entityStates){
    entityStates.forEach(entityState -> {
      Entity entity = idHashTable.get(entityState.getId());
      Point2D currPos = entity.getPosition();
      Point2D newPos = entity.getPosition();
      PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);
      //entity.getTransformComponent().set
      entity.setPosition(new Vec2(newPos).add(currPos.multiply(-1)));
    });
    //HashMap<Integer,Entity> hashMap =
        //entityStream.map(entity -> new HashMap.SimpleEntry<> (entity.getComponent(IDComponent.class).getId(),entity));
        //HashMap<Integer,Entity> hashMap = new HashMap<>(entityStream.map();
        //newPacket.getAllCoordinatesOfEntitiesList().forEach(entityState -> {

        //});
  }
}
