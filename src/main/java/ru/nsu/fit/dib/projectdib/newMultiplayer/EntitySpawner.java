package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.entity.Entity;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javafx.application.Platform;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;

public final class EntitySpawner {

  private EntitySpawner() {
    throw new UnsupportedOperationException();
  }

  public static Future<Entity> spawn(NewEntity newEntity) {
    return CompletableFuture.supplyAsync(() -> {
      final Entity[] entity = {null};
      Thread compThread = Thread.currentThread();
      Platform.runLater(() -> {
        //Принят для исполнения или принят чтоб передать версеру?
        //Где передается серверу?
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
