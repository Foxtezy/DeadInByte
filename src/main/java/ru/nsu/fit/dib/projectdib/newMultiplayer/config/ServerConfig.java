package ru.nsu.fit.dib.projectdib.newMultiplayer.config;


import java.net.Socket;
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
    MCServer.addBean(EMCServer.SENDER, new Sender());
    MCServer.addBean(EMCServer.RECEIVER, new Receiver());
  }

  public static void addClientAddresses(Map<Integer, Socket> sockets) {
    MCServer.addBean(EMCServer.CLIENT_SOCKETS, sockets);
  }

  public static void addServerThread(ServerThread thread) {
    MCServer.addBean(EMCServer.SERVER_THREAD, thread);
  }
}
