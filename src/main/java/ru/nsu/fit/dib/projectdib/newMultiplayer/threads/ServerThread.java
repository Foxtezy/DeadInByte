package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionStatus;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketTypeException;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public class ServerThread extends Thread {


  private final Receiver receiver;

  private final Sender sender;

  private final List<SocketAddress> clientSockets;

  private Integer nextEntityId = 1;

  public ServerThread(Receiver receiver, Sender sender, List<SocketAddress> clientSockets) {
    this.receiver = receiver;
    this.sender = sender;
    this.clientSockets = clientSockets;
  }


  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      GameStatePacket inPacket;
      try {
        inPacket = receiver.receive();
      } catch (PacketTypeException e) {
        // TODO: 14.03.2023 обработка ошибки
        continue;
      } catch (SocketTimeoutException e) {
        // сеть упала
        throw new RuntimeException("сеть упала=(" + e);
      }
      //Спавнит по запросам всегда
      inPacket.getActions().getSpawnActions().values().stream()
          .filter(gameAction -> gameAction.getStatus() == ActionStatus.CREATED).forEach(spawnAction -> {
        spawnAction.setStatus(ActionStatus.APPROVED);
        spawnAction.getNewEntity().setId(nextEntityId++);
        if (HeroType.getByName(spawnAction.getNewEntity().getEntityType())!=null) spawnAction.getNewEntity().setWeaponId(nextEntityId++);
      });
      GameStatePacket outPacket = inPacket;
      clientSockets.forEach(s -> sender.send(s, outPacket));
    }
  }
}
