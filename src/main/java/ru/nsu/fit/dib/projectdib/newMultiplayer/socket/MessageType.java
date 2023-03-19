package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

public enum MessageType {
  ERROR((byte) 0),
  NEW_STATE((byte) 1),
  MAP((byte) 2);

  private final byte id;

  MessageType(byte id) {
    this.id = id;
  }

  public byte getId() {
    return id;
  }
}
