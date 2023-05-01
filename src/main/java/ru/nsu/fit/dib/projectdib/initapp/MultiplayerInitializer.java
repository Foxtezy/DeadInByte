package ru.nsu.fit.dib.projectdib.initapp;

import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ClientConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientReceiverThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientSenderThread;

public class MultiplayerInitializer {

  public static volatile boolean endOfInit = false;

  public void run() {
    Sender sender = new Sender();
    sender.send(MCClient.getClientSocket(), new Pair<>(MessageType.START_INIT, null));
    ClientConfig.addClientReceiverThread(new ClientReceiverThread());
    //ждем конца инициаллизации
    while (!endOfInit);
    ClientConfig.addClientSenderThread(new ClientSenderThread());
  }

}
