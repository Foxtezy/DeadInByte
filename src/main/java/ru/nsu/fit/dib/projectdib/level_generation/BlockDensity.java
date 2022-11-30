package ru.nsu.fit.dib.projectdib.level_generation;

public enum BlockDensity {
  FLOOR(4),
  WAY(3),
  WALL(2000);
  public final int density;

  BlockDensity(int density) {
    this.density = density;
  }
}
