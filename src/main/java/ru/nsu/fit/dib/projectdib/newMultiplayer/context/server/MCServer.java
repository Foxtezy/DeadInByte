package ru.nsu.fit.dib.projectdib.newMultiplayer.context.server;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.ContextException;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerActionThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ServerUpdaterThread;

public final class MCServer {


  private MCServer() {
    throw new UnsupportedOperationException();
  }

  private static final HashMap<EMCServer, Object> context = new HashMap<>();

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
}
