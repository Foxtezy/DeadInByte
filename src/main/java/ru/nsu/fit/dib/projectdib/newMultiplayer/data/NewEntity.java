package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import javafx.geometry.Point2D;

public class NewEntity {

  private Integer id;
  private final String name;
  private Point2D position;
  private String entityType; //HeroType, WeaponType, EnemyType
  private Integer seed; //сид для генерации игрока

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEntityType() {
    return entityType;
  }

  public Point2D getPosition() {
    return position;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public NewEntity(Integer id, String name, Point2D position, String entityType, Integer seed) {
    this.id = id;
    this.name = name;
    this.position = position;
    this.entityType = entityType;
    this.seed = seed;
  }
}
