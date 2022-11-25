package ru.nsu.fit.dib.projectdib.room_generation;

import ru.nsu.fit.dib.projectdib.level_generation.Area.Area;
import ru.nsu.fit.dib.projectdib.level_generation.Level;

public class RoomFactory {
  public void makeRoom(Level level, Area area, RoomType roomType) {
    switch (roomType) {
      case CRYPT:
        break;
      case GARDEN:
        break;
      case LIBRARY:
        break;
    }
  }
}
