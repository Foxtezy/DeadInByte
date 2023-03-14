package ru.nsu.fit.dib.projectdib.multiplayer;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import ru.nsu.fit.dib.projectdib.multiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.multiplayer.exeptions.PacketTypeException;
import ru.nsu.fit.dib.projectdib.multiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.multiplayer.socket.Sender;

public class ServerThread implements Runnable {

  private Thread thread;

  private final Receiver receiver;

  private final Sender sender;

  private final List<SocketAddress> clientSockets;

  private Integer nextEntityId = 1;

  public ServerThread(DatagramSocket serverSocket, List<SocketAddress> clientSockets) {
    this.receiver = new Receiver(serverSocket);
    this.sender = new Sender(serverSocket);
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
        throw new RuntimeException(e);
      }

      // логика обработки пакета :)
      inPacket.getNewEntityList().forEach(e -> {
        e.setId(nextEntityId);
        nextEntityId++;
      });

      GameStatePacket outPacket = inPacket;

      clientSockets.forEach(s -> sender.send(s, outPacket));
    }
  }
}
