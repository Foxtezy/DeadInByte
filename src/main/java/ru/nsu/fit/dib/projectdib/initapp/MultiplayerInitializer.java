package ru.nsu.fit.dib.projectdib.initapp;

import java.io.IOException;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ClientConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientReceiverThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientSenderThread;

/**
 * Запускать после MapInitializer
 */
public class MultiplayerInitializer {

  public static volatile boolean endOfInit = false;

  public void run() {
    Sender sender = new Sender();
    try {
      sender.send(MCClient.getClientSocket(), new Pair<>(MessageType.START_INIT, null));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    //ждем конца инициаллизации
    while (!endOfInit);
    ClientConfig.addClientSenderThread(new ClientSenderThread());
  }

}
