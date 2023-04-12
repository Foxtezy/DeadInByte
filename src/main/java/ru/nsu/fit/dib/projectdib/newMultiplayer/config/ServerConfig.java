package ru.nsu.fit.dib.projectdib.newMultiplayer.config;

import com.almasb.fxgl.entity.SpawnData;
import com.google.gson.GsonBuilder;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    MCServer.addBean(EMCServer.SENDER, new Sender(null, null));
    MCServer.addBean(EMCServer.RECEIVER, new Receiver(null, null));
  }

  public static void addClientAddresses(Map<Integer, Socket> sockets) {
    MCServer.addBean(EMCServer.CLIENT_SOCKETS, sockets);
  }

  public static void addServerThread(ServerThread thread) {
    MCServer.addBean(EMCServer.SERVER_THREAD, thread);
  }
}
