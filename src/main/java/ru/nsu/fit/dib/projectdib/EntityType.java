package ru.nsu.fit.dib.projectdib;

/**
 * enumeration of Entities.
 */
public enum EntityType {

  PLAYER("player"),
  BOX("box"),
  TREE("tree"),
  COIN("coin"),
  ARROW("arrow"),
  BULLET("bullet"),
  ENEMY("enemy"),
  WALL("wall"),
  PLATFORM("platform"),
  CLOSED_DOOR("closedDoor"),
  OPENED_DOOR("openedDoor"),
  BUTTON("button"),
  DOOR_TRIGGER("doorTrigger"),
  CHEST("chest"),
  BOW("bow"),
  AK("ak");

  private final String name;

  EntityType(String name) {
    this.name = name;
  }


  public String getName() {
    return name;
  }
}
