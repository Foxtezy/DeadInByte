package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.entity.Entity;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;

public final class EntitySpawner {

  private EntitySpawner() {
    throw new UnsupportedOperationException();
  }

  public static Future<Entity> spawn(NewEntity newEntity) {
    return CompletableFuture.supplyAsync(() -> MCClient.getClientState().acceptedSpawn(newEntity));
  }
  public static void doAction(Pair<MessageType, Object> action) {
    MCClient.getClientState().acceptedAction(action);
  }
}
