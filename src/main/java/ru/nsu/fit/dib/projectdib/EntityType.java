package ru.nsu.fit.dib.projectdib;

import com.almasb.fxgl.entity.Entity;

/**
 * enumeration of Entities.
 */
public enum EntityType {

  PLAYER("player"),
  BOX("box"),
  TREE("tree"),
  COIN("coin"),
  PROJECTILE("projectile"),
  ENEMY("enemy"),
  WALL("wall"),
  PLATFORM("platform"),
  CLOSED_DOOR("closedDoor"),
  OPENED_DOOR("openedDoor"),
  BUTTON("button"),
  DOOR_TRIGGER("doorTrigger"),
  CHEST("chest"),
  BOW("bow"),
  AK("ak"),
<<<<<<< HEAD
  GUN("gun"),
  SHOTGUN("shotgun");
=======
  EXPLOSION("explosion");

>>>>>>> origin/develop
  private final String name;

  EntityType(String name) {
    this.name = name;
  }


  public String getName() {
    return name;
  }
}
