package ru.nsu.fit.dib.projectdib.newMultiplayer.socket;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MessageType {
  ERROR((byte) 0),
  UPDATE((byte) 1),
  SPAWN((byte) 2),
  WEAPON((byte) 3),
  ACTION((byte) 4),
  MAP_SEED((byte) 5),
  START_INIT((byte) 6),
  END_INIT((byte) 7);

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
