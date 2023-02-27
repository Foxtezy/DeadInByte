package ru.nsu.fit.dib.projectdib.multiplayer.data;

import javafx.geometry.Point2D;

public class EntityState {
  private final Integer id;
  private final Point2D coordinate;
  private final Double angle;
  public Integer getId() {
    return id;
  }

  public Point2D getCoordinate() {
    return coordinate;
  }

  public Double getAngle() {
    return angle;
  }
  public EntityState(Integer id, Point2D coordinate, Double angle){
    this.id=id;
    this.coordinate=coordinate;
    this.angle=angle;
  }
}
