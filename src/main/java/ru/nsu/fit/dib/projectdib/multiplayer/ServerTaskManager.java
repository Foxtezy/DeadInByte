package ru.nsu.fit.dib.projectdib.multiplayer;

import com.almasb.fxgl.entity.Entity;
import ru.nsu.fit.dib.projectdib.entity.components.PlayerComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.NewEntity;

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
