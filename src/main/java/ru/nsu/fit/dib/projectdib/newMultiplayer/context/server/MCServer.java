package ru.nsu.fit.dib.projectdib.newMultiplayer.context.server;

import com.google.gson.Gson;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.EMCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.ContextException;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

public final class MCServer {


  private MCServer() {
    throw new UnsupportedOperationException();
  }

  private static final HashMap<EMCServer, Object> context = new HashMap<>();

  public static void addBean(EMCServer emcServer, Object bean) {
    context.put(emcServer, bean);
  }


  public static ClientThread getClientThread() {
    if (context.get(EMCServer.SERVER_THREAD) instanceof ClientThread t) {
      return t;
    } else {
      throw new ContextException();
    }
  }

  public static Map<Integer, Socket> getClientSockets() {
    if (context.get(EMCServer.CLIENT_SOCKETS) instanceof List<?> l) {
      return (Map<Integer, Socket>) l;
    } else {
      throw new ContextException();
    }
  }

  public static Sender getSender() {
    if (context.get(EMCServer.SENDER) instanceof Sender s) {
      return s;
    } else {
      throw new ContextException();
    }
  }

  public static Receiver getReceiver() {
    if (context.get(EMCServer.RECEIVER) instanceof Receiver r) {
      return r;
    } else {
      throw new ContextException();
    }
  }
}
