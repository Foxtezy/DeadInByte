package ru.nsu.fit.dib.projectdib.newMultiplayer.context.client;

import com.google.gson.Gson;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import ru.nsu.fit.dib.projectdib.newMultiplayer.ClientState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.ContextException;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;

/**
 * Multiplayer context.
 */
public final class MCClient {

  private MCClient() {
    throw new UnsupportedOperationException();
  }

  private static final HashMap<EMCClient, Object> context = new HashMap<>();

  public static void addBean(EMCClient emcClient, Object bean) {
    context.put(emcClient, bean);
  }

  public static DatagramSocket getClientSocket() {
    if (context.get(EMCClient.CLIENT_SOCKET) instanceof DatagramSocket s) {
      return s;
    } else {
      throw new ContextException();
    }
  }

  public static ClientThread getClientThread() {
    if (context.get(EMCClient.CLIENT_THREAD) instanceof ClientThread t) {
      return t;
    } else {
      throw new ContextException();
    }
  }

  public static SocketAddress getServerAddress() {
    if (context.get(EMCClient.SERVER_ADDRESS) instanceof SocketAddress s) {
      return s;
    } else {
      throw new ContextException();
    }
  }

  public static Sender getSender() {
    if (context.get(EMCClient.SENDER) instanceof Sender s) {
      return s;
    } else {
      throw new ContextException();
    }
  }

  public static Receiver getReceiver() {
    if (context.get(EMCClient.RECEIVER) instanceof Receiver r) {
      return r;
    } else {
      throw new ContextException();
    }
  }

  public static ClientState getClientState() {
    if (context.get(EMCClient.CLIENT_STATE) instanceof ClientState c) {
      return c;
    } else {
      throw new ContextException();
    }
  }

  public static Gson getGson() {
    if (context.get(EMCClient.GSON) instanceof Gson gson) {
      return gson;
    } else {
      throw new ContextException();
    }
  }
}
