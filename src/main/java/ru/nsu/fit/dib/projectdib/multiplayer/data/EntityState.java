package ru.nsu.fit.dib.projectdib.multiplayer.data;

import javafx.geometry.Point2D;

public class EntityState {
  private final Integer id;
  private Point2D coordinate;
  private Point2D angle;
  public Integer getId() {
    return id;
  }

  public Point2D getCoordinate() {
    return coordinate;
  }

  public Point2D getAngle() {
    return angle;
  }
  
  public void setCoordinate(Point2D newCoordinate){
    this.coordinate = newCoordinate;
  }
  public void setAngle(Point2D newAngle){
    this.angle = newAngle;
  }
  public EntityState(Integer id, Point2D coordinate, Point2D angle){
    this.id=id;
    this.coordinate=coordinate;
    this.angle=angle;
  }
}
