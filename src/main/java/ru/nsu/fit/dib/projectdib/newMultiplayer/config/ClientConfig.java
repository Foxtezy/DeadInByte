package ru.nsu.fit.dib.projectdib.newMultiplayer.config;

import com.almasb.fxgl.entity.SpawnData;
import com.google.gson.GsonBuilder;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.newMultiplayer.ClientState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientReceiverThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientSenderThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.EMCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public final class ClientConfig {

  private ClientConfig() {
    throw new UnsupportedOperationException();
  }

  public static void init() {
    MCClient.addBean(EMCClient.CLIENT_SOCKET, new Socket());
    MCClient.addBean(EMCClient.SENDER, new Sender(null, null));
    MCClient.addBean(EMCClient.RECEIVER, new Receiver(null, null));
    MCClient.addBean(EMCClient.CLIENT_STATE, new ClientState());
  }

  public static void addClientId(Integer id) {
    MCClient.addBean(EMCClient.CLIENT_ID, id);
  }

  public static void addClientSenderThread(ClientSenderThread thread) {
    MCClient.addBean(EMCClient.CLIENT_SENDER_THREAD, thread);
  }

  public static void addClientReceiverThread(ClientReceiverThread thread) {
    MCClient.addBean(EMCClient.CLIENT_RECEIVER_THREAD, thread);
  }
}
