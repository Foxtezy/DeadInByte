package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import com.almasb.fxgl.entity.Entity;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketTypeException;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public class ClientThread extends Thread {

  private final SocketAddress serverAddress;

  private final Sender sender;

  private final Receiver receiver;

  private final BlockingQueue<NewEntity> newEntities = new LinkedBlockingQueue<>();

  private final BlockingQueue<Entity> spawnEntityList = new LinkedBlockingQueue<>();

  private final Object monitor = new Object();

  public ClientThread(Receiver receiver, Sender sender, SocketAddress serverAddress) {
    this.serverAddress = serverAddress;
    this.sender = sender;
    this.receiver = receiver;
  }

  public Entity spawnNewEntity(NewEntity newEntity) {
    newEntities.add(newEntity);
    while (true) {
      synchronized (monitor) {
        try {
          monitor.wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
      }
      Point2D entPos = new Point2D(newEntity.getSpawnData().getX(),
          newEntity.getSpawnData().getX()); //могут быть проблемы
      Optional<Entity> entity = spawnEntityList.stream().filter(e -> e.getPosition().equals(entPos))
          .findAny();
      if (entity.isPresent()) {
        spawnEntityList.remove(entity.get());
        return entity.get();
      }
    }
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      List<EntityState> entityStates = MCClient.getClientState().getEntityStates();
      List<NewEntity> newEntityList = new ArrayList<>();
      newEntities.drainTo(newEntityList);
      sender.send(serverAddress, new GameStatePacket(newEntityList, entityStates));
      try {
        GameStatePacket gameStatePacket = receiver.receive();
        MCClient.getClientState().updateEntities(gameStatePacket.getAllCoordinatesOfEntitiesList());
        List<Entity> entityList = MCClient.getClientState().spawnEntities(gameStatePacket.getNewEntityList());
        spawnEntityList.addAll(entityList);
        synchronized (monitor) {
          monitor.notifyAll();
        }
      } catch (PacketTypeException e) {
        throw new RuntimeException(e);
      } catch (SocketTimeoutException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
