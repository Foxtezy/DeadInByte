package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.newentities;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

public abstract class NewEntity {

  public NewEntity(Point2D position) {
    this.position = position;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  protected Integer id;
  protected Point2D position;

  /**
   * Spawns and adds entity at the Hashmap.
   *
   * @return spawned entity
   */
  public abstract Entity spawn();
}
