package ru.nsu.fit.dib.projectdib.newMultiplayer.config;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import ru.nsu.fit.dib.projectdib.connecting.tasks.ServerConnectionThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.ServerState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerActionThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.EMCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerReceiverThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerUpdaterThread;

public final class ServerConfig {

  private ServerConfig() {
    throw new UnsupportedOperationException();
  }

  public static void init() {
    MCServer.addBean(EMCServer.SERVER_STATE, new ServerState());
    addServerActionThread(new ServerActionThread());
    addServerUpdaterThread(new ServerUpdaterThread());
    addClientSockets(new ConcurrentHashMap<>());
    addServerReceiverThreads(new CopyOnWriteArrayList<>());
  }

  public static void addClientSockets(Map<Integer, Socket> sockets) {
    MCServer.addBean(EMCServer.CLIENT_SOCKETS, sockets);
  }

  public static void addServerActionThread(ServerActionThread thread) {
    thread.start();
    MCServer.addBean(EMCServer.SERVER_ACTION_THREAD, thread);
  }

  public static void addServerUpdaterThread(ServerUpdaterThread thread) {
    thread.start();
    MCServer.addBean(EMCServer.SERVER_UPDATER_THREAD, thread);
  }

  public static void addServerConnectionThread(ServerConnectionThread t) {
    t.start();
    MCServer.addBean(EMCServer.SERVER_CONNECTION_THREAD, t);
  }

  public static void addServerReceiverThreads(List<ServerReceiverThread> t) {
    MCServer.addBean(EMCServer.SERVER_RECEIVER_THREADS, t);
  }
}
