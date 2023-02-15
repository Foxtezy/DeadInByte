package ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area;

public enum SizeType {
  OVERBIG(4),
  BIG(3),
  MIDDLE(2),
  SMALL(1),
  OVERSMALL(0);

  public final int size;

  SizeType(int size) {
    this.size = size;
  }
}
