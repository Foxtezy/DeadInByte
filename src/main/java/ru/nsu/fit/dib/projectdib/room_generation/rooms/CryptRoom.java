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
    makeGraves();
  }

  private void makeGraves() {
    Point pointer = new Point(leftUp);
    pointer.translate(1, 1);
    while (true) {
      makeGrave(pointer);
      if (rightDown.y - pointer.y < 3) {
        break;
      }
      pointer.translate(0, 5);
    }
    pointer = new Point(leftUp);
    pointer.translate(rightDown.x - 3, 1);
    while (true) {
      makeGrave(pointer);
      if (rightDown.y - pointer.y < 3) {
        break;
      }
      pointer.translate(0, 5);
    }
  }

  private void makeGrave(Point pointer) {
    graveTile(pointer);
    pointer.translate(1, 0);
    graveTile(pointer);
    pointer.translate(1, 0);
    graveTile(pointer);
    pointer.translate(-2, 1);
    graveTile(pointer);
    pointer.translate(1, 0);
    graveTile(pointer);
    pointer.translate(1, 0);
    graveTile(pointer);
  }

  private void graveTile(Point point) {
    level.map[point.x][point.y] = GRAVE;
  }


}
