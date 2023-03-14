package ru.nsu.fit.dib.projectdib.multiplayer;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.IDComponent;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.entity.components.PlayerComponent;
import ru.nsu.fit.dib.projectdib.multiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.multiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.multiplayer.data.NewEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class ServerTaskManager {

  private final Map<Integer, Entity> idServerHashTable = new HashMap<>();;
  private List<NewEntity> listNewEntities;
  private List<EntityState> listEntityState;
  //Вынес Executor и UpdateTask, чтобы было создано только по 1 экземпляру
  private final ExecutorService executorService = Executors.newCachedThreadPool();
  private final UpdateTask updateTask = new UpdateTask();

  public void mySpawn(String name, Point2D pos) {
    SpawnData spawnData = new SpawnData(pos); // TODO: 14.03.2023
    Entity spawned = spawn(name, pos);
    int newId = spawned.getComponent(IDComponent.class).getId();
    NewEntity spawnedNewEntity = new NewEntity(newId, name, spawnData);
    listNewEntities.add(spawnedNewEntity);
    EntityState spawnedEntityState = new EntityState(newId, spawned.getPosition(),
        spawned.getComponent(
            PlayerComponent.class).getMouseVelocity()); // TODO: 14.03.2023 Фигня
    listEntityState.add(spawnedEntityState);
    idServerHashTable.put(newId, spawned);
  }


  private class UpdateTask implements Callable<List<EntityState>> {

    @Override
    public List<EntityState> call() throws Exception {
      listEntityState.parallelStream().forEach(entState -> {
        int id = entState.getId();
        entState.setCoordinate(idServerHashTable.get(id).getPosition());
        entState.setAngle(
            idServerHashTable.get(id).getComponent(PlayerComponent.class).getMouseVelocity());
      });
      return listEntityState;
    }
  }

  public GameStatePacket makePacket() {
    Future<List<EntityState>> updatedListEntityState = executorService.submit(updateTask);
    GameStatePacket packet = new GameStatePacket(listNewEntities, listEntityState);
    listNewEntities.clear();
    return packet;
  }
}
