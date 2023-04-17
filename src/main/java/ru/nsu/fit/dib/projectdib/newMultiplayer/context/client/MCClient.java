package ru.nsu.fit.dib.projectdib.newMultiplayer.context.client;

import java.net.Socket;
import java.util.HashMap;
import ru.nsu.fit.dib.projectdib.newMultiplayer.ClientState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientReceiverThread;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientSenderThread;
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

  public static Integer getClientId() {
    if (context.get(EMCClient.CLIENT_ID) instanceof Integer i) {
      return i;
    } else {
      throw new ContextException();
    }
  }

  public static Socket getClientSocket() {
    if (context.get(EMCClient.CLIENT_SOCKET) instanceof Socket s) {
      return s;
    } else {
      throw new ContextException();
    }
  }

  public static ClientSenderThread getClientSenderThread() {
    if (context.get(EMCClient.CLIENT_SENDER_THREAD) instanceof ClientSenderThread t) {
      return t;
    } else {
      throw new ContextException();
    }
  }

  public static ClientReceiverThread getClientReceiverThread() {
    if (context.get(EMCClient.CLIENT_RECEIVER_THREAD) instanceof ClientReceiverThread t) {
      return t;
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
}
