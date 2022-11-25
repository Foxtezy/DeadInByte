package ru.nsu.fit.dib.projectdib.room_generation.rooms;

import java.awt.Point;
import ru.nsu.fit.dib.projectdib.level_generation.Area.Area;
import ru.nsu.fit.dib.projectdib.level_generation.Level;

/**
 * min wight = 12, min height 14
 */
public class CryptRoom extends AbstractRoom {

  private static final int GRAVE = 5;

  public CryptRoom(Level level, Area area) {
    super(level, area);
  }

  @Override
  public void makeRoom() {

  }

  private void makeGraves() {

  }

  private void graveTile(Point point) {
    level.map[point.x][point.y] = GRAVE;
  }


}
