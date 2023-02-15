package ru.nsu.fit.dib.projectdib.environment.level_generation;

/**
 * Плотность блока. Главным образом влияет на построение карты.
 */
public enum BlockDensity {
  FLOOR(4),
  WAY(3),
  WALL(2000);
  public final int density;

  BlockDensity(int density) {
    this.density = density;
  }
}
