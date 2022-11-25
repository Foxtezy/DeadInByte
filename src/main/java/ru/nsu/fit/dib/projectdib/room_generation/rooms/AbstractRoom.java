package ru.nsu.fit.dib.projectdib.room_generation.rooms;

import java.awt.Point;
import ru.nsu.fit.dib.projectdib.level_generation.Area.Area;
import ru.nsu.fit.dib.projectdib.level_generation.Level;

public abstract class AbstractRoom {

  protected final Level level;
  protected final Point leftUp;
  protected final Point rightDown;

  public AbstractRoom(Level level, Area area) {
    this.level = level;
    leftUp = area.getFirstPoint();
    rightDown = area.getSecondPoint();
  }

  public abstract void makeRoom();

}
