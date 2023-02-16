package ru.nsu.fit.dib.projectdib.environment.level_generation.RoomGeneration;

import static ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.BIG;
import static ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.MIDDLE;

import java.awt.Point;
import java.util.Random;
import ru.nsu.fit.dib.projectdib.environment.level_generation.BlockDensity;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.SizeType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.BPGraph;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;

/**
 * Генератор пустых комнат. Генерирует пустю комнату в каждой Area.
 */
public class EmptyRoomGenerator {

  private BPGraph graph;
  private Level level;
  private Random rn;

  public EmptyRoomGenerator(BPGraph graph, Level level) {
    this.graph = graph;
    this.level = level;
    this.rn = level.rn;
  }

  public void generateRoom(SizeType type) {
    switch (type) {
      case BIG -> generateGigRooms();
      case MIDDLE -> generateMiddleRooms();
      case SMALL -> generateSmallRooms();
    }
  }

  private void generateGigRooms() {

    graph.getAreaList().forEach(area -> {
      if (area.getSizeType() != BIG) {
        return;
      }
      Point fst;
      Point snd;
      fst = new Point(
          area.getFirstPoint().x + 1 + rn.nextInt(area.getWidth() / 10 + 2),
          area.getFirstPoint().y + 1 + rn.nextInt(area.getHeight() / 10 + 2));
      snd = new Point(
          area.getSecondPoint().x - 1 - rn.nextInt(area.getWidth() / 10 + 2),
          area.getSecondPoint().y - 1 - rn.nextInt(area.getHeight() / 10 + 2));
      level.set(BlockDensity.FLOOR, fst, snd);
      area.setRoom(fst, snd);
    });
  }

  private void generateMiddleRooms() {
    graph.getAreaList().forEach(area -> {
      if (area.getSizeType() != MIDDLE) {
        return;
      }
      Point fst;
      Point snd;
      fst = new Point(
          area.getFirstPoint().x + 1 + rn.nextInt(area.getWidth() / 10 + 2),
          area.getFirstPoint().y + 1 + rn.nextInt(area.getHeight() / 10 + 2));
      snd = new Point(
          area.getSecondPoint().x - 1 - rn.nextInt(area.getWidth() / 10 + 2),
          area.getSecondPoint().y - 1 - rn.nextInt(area.getHeight() / 10 + 2));
      level.set(BlockDensity.FLOOR, fst, snd);
      area.setRoom(fst, snd);
    });
  }

  private void generateSmallRooms() {
    graph.getAreaList().forEach(area -> {
      if (area.getSizeType() != SizeType.SMALL) {
        return;
      }
      Point fst;
      Point snd;
      fst = new Point(area.getFirstPoint().x + 1 + rn.nextInt(2),
          area.getFirstPoint().y + 1 + rn.nextInt(2));
      snd = new Point(area.getSecondPoint().x - 1 - rn.nextInt(2),
          area.getSecondPoint().y - 1 - rn.nextInt(2));
      level.set(BlockDensity.FLOOR, fst, snd);
      area.setRoom(fst, snd);
    });
  }
}
