package ru.nsu.fit.dib.projectdib.room_generation;

public enum RoomType {

  CRYPT(12, 14),
  GARDEN(20, 20),
  LIBRARY(10, 10);

  private final int wight;

  private final int height;

  RoomType(int wight, int height) {
    this.wight = wight;
    this.height = height;
  }

  public int getWight() {
    return wight;
  }

  public int getHeight() {
    return height;
  }
}
