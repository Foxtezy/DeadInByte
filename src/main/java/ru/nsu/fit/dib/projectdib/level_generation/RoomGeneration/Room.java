package ru.nsu.fit.dib.projectdib.level_generation.RoomGeneration;

import static java.lang.Math.abs;

import java.awt.Point;
import java.io.Serializable;

/**
 * Комната занимает некоторую область на карте (x2-x1)*tileX + (y2-y1)*tileY
 * tileX, tileY - длина и ширина тайла
 * x1,y1 - координаты первой точки
 * x2, y2 - координаты 2й точки
 * координаты точки можно получить с помощью:
 * getFirstPoint()
 * getSecondPoint()
 */
public class Room implements Serializable {

  private final Point firstPoint;
  private final Point firstPointWithWalls;
  private final Point secondPoint;
  private final Point secondPointWithWalls;
  private final Point centrePoint;
  private final int weight;
  private final int width;
  private final int height;
  private RoomType roomType;

  public Room(Point firstPoint, Point secondPoint) {
    this.firstPoint = firstPoint;
    this.secondPoint = secondPoint;
    this.firstPointWithWalls = new Point(firstPoint.x - 1, firstPoint.y - 1);
    this.secondPointWithWalls = new Point(secondPoint.x + 1, secondPoint.y + 1);
    width = abs(firstPoint.x - secondPoint.x) + 1;
    height = abs(firstPoint.y - secondPoint.y) + 1;
    centrePoint = new Point(firstPoint.x + width / 2, firstPoint.y + height / 2);
    weight = width * height;
  }

  public Point getFirstPointWithWalls() {
    return firstPointWithWalls;
  }

  public Point getSecondPointWithWalls() {
    return secondPointWithWalls;
  }

  public boolean equals(Point point) {
    return firstPoint.x <= point.x && secondPoint.x >= point.x && firstPoint.y <= point.y
        && secondPoint.y >= point.y;
  }

  public Point getFirstPoint() {
    return firstPoint;
  }

  public Point getSecondPoint() {
    return secondPoint;
  }

  public RoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Point getCentrePoint() {
    return centrePoint;
  }

  public int getWeight() {
    return weight;
  }
}
