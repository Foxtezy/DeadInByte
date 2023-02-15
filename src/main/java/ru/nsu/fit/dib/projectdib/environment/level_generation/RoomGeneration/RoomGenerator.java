package ru.nsu.fit.dib.projectdib.environment.level_generation.RoomGeneration;

import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;

public class RoomGenerator {

  int seed;
  Level level;

  public RoomGenerator(int seed, Level level) {
    this.seed = seed;
    this.level = level;
  }

  public void generate(RoomType type, Room room) {
    switch (type) {
      case EMPTY -> {
        generateEmptyRoom(room);
      }
    }
  }

  //Example of room
  private void generateEmptyRoom(Room room) {
    //Good job!
  }
}
