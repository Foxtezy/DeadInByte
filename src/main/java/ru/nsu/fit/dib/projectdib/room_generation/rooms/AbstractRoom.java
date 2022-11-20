package ru.nsu.fit.dib.projectdib.room_generation.rooms;

import ru.nsu.fit.dib.projectdib.level_generation.Area.Area;

public abstract class AbstractRoom {

  protected final Area area;

  protected final int[][] map;

  public AbstractRoom(Area area) {
    this.area = area;
    map = new int[area.getHeight()][area.getWidth()];
  }

  public abstract void makeRoom();

}
