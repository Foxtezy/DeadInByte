package ru.nsu.fit.dib.projectdib.room_generation;

import ru.nsu.fit.dib.projectdib.level_generation.Area.Area;
import ru.nsu.fit.dib.projectdib.level_generation.Level;
import ru.nsu.fit.dib.projectdib.room_generation.rooms.CryptRoom;

public class RoomFactory {
  public void makeRoom(Level level, Area area, RoomType roomType) {
    switch (roomType) {
      case CRYPT:
        CryptRoom cryptRoom = new CryptRoom(level, area);
        cryptRoom.makeRoom();
        break;
      case GARDEN:
        break;
      case LIBRARY:
        break;
    }
  }
}
