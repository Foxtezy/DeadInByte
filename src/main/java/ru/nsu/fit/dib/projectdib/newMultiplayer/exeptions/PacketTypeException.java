package ru.nsu.fit.dib.projectdib.newMultiplayer.exeptions;

import ru.nsu.fit.dib.projectdib.newMultiplayer.data.GameStatePacket;

public class PacketTypeException extends Exception {

  private final GameStatePacket gameStatePacket;


  public PacketTypeException(GameStatePacket gameStatePacket) {
    this.gameStatePacket = gameStatePacket;
  }

  public GameStatePacket getGameStatePacket() {
    return gameStatePacket;
  }
}
