package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions.PacketTypeException;

public class Receiver {

  private final DatagramSocket socket;

  private final Gson gson;

  public Receiver(DatagramSocket socket, Gson gson) {
    this.socket = socket;
    this.gson = gson;
  }

  public GameStatePacket receive() throws PacketTypeException, SocketTimeoutException {
    try {
      socket.setSoTimeout(10000);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
    byte[] byteArray = new byte[60000];

    /*
    DatagramPacket p = new DatagramPacket(byteArray, byteArray.length);
    try {
      socket.receive(p);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    */
    switch (MessageType.getMessageType(byteArray[0])){
      case UPDATE -> {
        /*
        long startTime = System.currentTimeMillis();
        ByteBuffer bb = ByteBuffer.wrap(byteArray);
        reposListFlat = frogermcs.io.flatbuffs.model.flat.ReposList.getRootAsReposList(bb);
        for (int i = 0; i < reposListFlat.reposLength(); i++) {
          Repo repos = reposListFlat.repos(i);
          Log.d("FlatBuffers", "Repo #" + i + ", id: " + repos.id());
        }
        long endTime = System.currentTimeMillis() - startTime;
        tvFlat.setText("Elements: " + reposListFlat.reposLength() + ": load time: " + endTime + "ms");

        return*/
      }

    }
/*
    if (p.getData()[0] != MessageType.UPDATE.getId()) {
      String json = new String(p.getData(), 1, p.getLength() - 1, StandardCharsets.UTF_8);
      throw new PacketTypeException(new Gson().fromJson(json, GameStatePacket.class));
    }
    String json = new String(p.getData(), 1, p.getLength() - 1, StandardCharsets.UTF_8);*/
    //return new Gson().fromJson(null, GameStatePacket.class);
    return null;
  }
}
