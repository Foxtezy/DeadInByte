package ru.nsu.fit.dib.projectdib.newMultiplayer.config;

import java.net.Socket;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerActionThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.EMCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerUpdaterThread;

public final class ServerConfig {

  private ServerConfig() {
    throw new UnsupportedOperationException();
  }

  public static void init() {

  }

  public static void addClientAddresses(Map<Integer, Socket> sockets) {
    MCServer.addBean(EMCServer.CLIENT_SOCKETS, sockets);
  }

  public static void addServerActionThread(ServerActionThread thread) {
    MCServer.addBean(EMCServer.SERVER_ACTION_THREAD, thread);
  }

  public static void addServerUpdaterThread(ServerUpdaterThread thread) {
    MCServer.addBean(EMCServer.SERVER_UPDATER_THREAD, thread);
  }
}
