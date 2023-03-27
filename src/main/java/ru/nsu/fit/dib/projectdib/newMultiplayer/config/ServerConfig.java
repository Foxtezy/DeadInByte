package ru.nsu.fit.dib.projectdib.newMultiplayer.config;

import com.almasb.fxgl.entity.SpawnData;
import com.google.gson.GsonBuilder;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.EMCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public final class ServerConfig {

  private ServerConfig() {
    throw new UnsupportedOperationException();
  }

  public static void init() {
    MCServer.addBean(EMCServer.GSON, new GsonBuilder().serializeNulls()
        .create());
    addServerSocket();

    MCServer.addBean(EMCServer.SENDER, new Sender(MCServer.getServerSocket(), MCServer.getGson()));
    MCServer.addBean(EMCServer.RECEIVER, new Receiver(MCServer.getServerSocket(), MCServer.getGson()));
  }

  private static void addServerSocket() {
    DatagramSocket datagramSocket;
    try {
      datagramSocket = new DatagramSocket(8080);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
    MCServer.addBean(EMCServer.SERVER_SOCKET, datagramSocket);
  }

  public static void addClientAddresses(List<SocketAddress> socketAddresses) {
    MCServer.addBean(EMCServer.CLIENT_ADDRESSES, socketAddresses);
  }

  public static void addServerThread(ServerThread thread) {
    MCServer.addBean(EMCServer.SERVER_THREAD, thread);
  }
}
