package ru.nsu.fit.dib.projectdib.level_generation.Area;

import static java.lang.Math.abs;

import java.awt.Point;
import ru.nsu.fit.dib.projectdib.level_generation.Area.AreaSizeType.SizeType;

/**
 * Отдельная область на карте, в которой генерируется помещение
 */
public class Area {

  private final Point firstPoint;
  private final Point secondPoint;
  private final int width;
  private final int height;
  private SizeType sizeType;

  public Area(Point firstPoint, Point secondPoint) {
    this.firstPoint = firstPoint;
    this.secondPoint = secondPoint;
    width = abs(firstPoint.x - secondPoint.x) + 1;
    height = abs(firstPoint.y - secondPoint.y) + 1;
  }

  public Point getFirstPoint() {
    return firstPoint;
  }

  public Point getSecondPoint() {
    return secondPoint;
  }

  public SizeType getSizeType() {
    return sizeType;
  }

  public void setSizeType(
      SizeType sizeType) {
    this.sizeType = sizeType;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
