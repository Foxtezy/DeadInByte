package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public final class EntitySpawner {

  private EntitySpawner() {
    throw new UnsupportedOperationException();
  }

  public static Future<Entity> spawn(String entityName, Point2D position, String entityType,
      Integer seed) {
    NewEntity newEntity = new NewEntity(null, entityName, position, entityType, seed,null);
    return CompletableFuture.supplyAsync(() -> {
      final Entity[] entity = {null};
      Thread compThread = Thread.currentThread();
      Platform.runLater(() -> {
        entity[0] = MCClient.getClientState().acceptedSpawn(newEntity);
        compThread.interrupt();
      });
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
      return entity[0];
    });
  }
}
