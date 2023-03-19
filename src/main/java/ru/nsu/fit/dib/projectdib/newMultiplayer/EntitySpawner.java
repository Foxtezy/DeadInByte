package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public final class EntitySpawner {

  private EntitySpawner() {
    throw new UnsupportedOperationException();
  }

  public static Future<Entity> spawn(String entityName, SpawnData spawnData) {
    NewEntity newEntity = new NewEntity(null, entityName, spawnData);
    return CompletableFuture.supplyAsync(() -> MCClient.getClientState()
        .acceptedSpawn(newEntity));
  }
}
