package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MessageType {
  ERROR((byte) 0),
  UPDATE((byte) 1),
  ACTION((byte) 2),
  MAP((byte) 3);

  private final byte id;
  private static final Map<Byte, MessageType> map = Arrays.stream(MessageType.values())
      .collect(Collectors.toMap(messageType -> messageType.id, orderStatus -> orderStatus));

  MessageType(byte id) {
    this.id = id;
  }

  public static MessageType getMessageType(byte id) {
    return map.get(id);
  }

  public byte getId() {
    return id;
  }
}
