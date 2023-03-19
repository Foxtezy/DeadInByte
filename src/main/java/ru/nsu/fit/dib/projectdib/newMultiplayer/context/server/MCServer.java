package ru.nsu.fit.dib.projectdib.newMultiplayer.context.server;

import com.google.gson.Gson;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
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

  public static DatagramSocket getServerSocket() {
    if (context.get(EMCServer.SERVER_SOCKET) instanceof DatagramSocket s) {
      return s;
    } else {
      throw new ContextException();
    }
  }

  public static ClientThread getClientThread() {
    if (context.get(EMCServer.SERVER_THREAD) instanceof ClientThread t) {
      return t;
    } else {
      throw new ContextException();
    }
  }

  public static List<SocketAddress> getClientAddresses() {
    if (context.get(EMCServer.CLIENT_ADDRESSES) instanceof List<?> l) {
      return (List<SocketAddress>) l;
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

  public static Gson getGson() {
    if (context.get(EMCServer.GSON) instanceof Gson gson) {
      return gson;
    } else {
      throw new ContextException();
    }
  }
}
