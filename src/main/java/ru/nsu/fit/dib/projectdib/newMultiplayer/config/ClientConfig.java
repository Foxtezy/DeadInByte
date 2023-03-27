package ru.nsu.fit.dib.projectdib.newMultiplayer.config;

import com.almasb.fxgl.entity.SpawnData;
import com.google.gson.GsonBuilder;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.newMultiplayer.ClientState;
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
    MCClient.addBean(EMCClient.GSON, new GsonBuilder().serializeNulls()
        .create());
    addClientSocket();
    MCClient.addBean(EMCClient.SENDER, new Sender(MCClient.getClientSocket(), MCClient.getGson()));
    MCClient.addBean(EMCClient.RECEIVER, new Receiver(MCClient.getClientSocket(), MCClient.getGson()));
    MCClient.addBean(EMCClient.CLIENT_STATE, new ClientState());
  }

  private static void addClientSocket() {
    DatagramSocket datagramSocket;
    try {
      datagramSocket = new DatagramSocket(9090);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
    MCClient.addBean(EMCClient.CLIENT_SOCKET, datagramSocket);
  }

  public static void addServerAddress(SocketAddress socketAddress) {
    MCClient.addBean(EMCClient.SERVER_ADDRESS, socketAddress);
  }

  public static void addClientThread(ClientThread thread) {
    MCClient.addBean(EMCClient.CLIENT_THREAD, thread);
  }
}
