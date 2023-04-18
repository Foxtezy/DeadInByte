package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import java.net.Socket;
import java.util.List;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;

public class ServerReceiverThread extends Thread {

  private final Receiver receiver;

  public ServerReceiverThread(Socket socket) {
    this.receiver = new Receiver(socket);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      var inPacket = receiver.receive();
      switch (inPacket.getKey()) {
        case UPDATE -> {
          MCServer.getUpdaterThread().addUpdateTask((List<EntityState>) inPacket.getValue());
        }
        default -> {
          MCServer.getActionThread().addActionTask(inPacket);
        }
      }
    }
  }
}
