package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.data.json.update.Action;

public class EntityState {
  private final Integer id;
  private Point2D position;
  private Point2D directionView; //для player
  private List<Action> actions; //для player

  public Integer getId() {
    return id;
  }

  public Point2D getPosition() {
    return position;
  }

  public Point2D getDirectionView() {
    return directionView;
  }
  
  public void setPosition(Point2D newCoordinate){
    this.position = newCoordinate;
  }
  public void setDirectionView(Point2D newAngle){
    this.directionView = newAngle;
  }
  public EntityState(Integer id, Point2D position, Point2D directionView, List<Action> actions){
    this.id=id;
    this.position = position;
    this.directionView = directionView;
    this.actions = actions;
  }
}
