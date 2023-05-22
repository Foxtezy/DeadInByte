package ru.nsu.fit.dib.projectdib.newMultiplayer.context.server;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ru.nsu.fit.dib.projectdib.connecting.tasks.ServerConnectionThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.ServerState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.ContextException;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerActionThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerReceiverThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerUpdaterThread;

public final class MCServer {


  private MCServer() {
    throw new UnsupportedOperationException();
  }

  private static final Map<EMCServer, Object> context = new ConcurrentHashMap<>();

  public static void clearContext() {
    context.clear();
  }

  public static void addBean(EMCServer emcServer, Object bean) {
    context.put(emcServer, bean);
  }


  public static Map<Integer, Socket> getClientSockets() {
    if (context.get(EMCServer.CLIENT_SOCKETS) instanceof Map<?, ?> l) {
      return (Map<Integer, Socket>) l;
    } else {
      throw new ContextException();
    }
  }

  public static ServerActionThread getActionThread() {
    if (context.get(EMCServer.SERVER_ACTION_THREAD) instanceof ServerActionThread t) {
      return t;
    } else {
      throw new ContextException();
    }
  }

  public static ServerUpdaterThread getUpdaterThread() {
    if (context.get(EMCServer.SERVER_UPDATER_THREAD) instanceof ServerUpdaterThread t) {
      return t;
    } else {
      throw new ContextException();
    }
  }

  public static ServerState getServerState() {
    if (context.get(EMCServer.SERVER_STATE) instanceof ServerState s) {
      return s;
    } else {
      throw new ContextException();
    }
  }

  public static ServerConnectionThread getConnectionThread() {
    if (context.get(EMCServer.SERVER_CONNECTION_THREAD) instanceof ServerConnectionThread t) {
      return t;
    } else {
      throw new ContextException();
    }
  }

  public static List<ServerReceiverThread> getReceiverThreads() {
    if (context.get(EMCServer.SERVER_RECEIVER_THREADS) instanceof List<?> t) {
      return (List<ServerReceiverThread>) t;
    } else {
      throw new ContextException();
    }
  }
}
