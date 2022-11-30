package ru.nsu.fit.dib.projectdib.level_generation.RoomGeneration;

import static ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.BIG;
import static ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.MIDDLE;
import static ru.nsu.fit.dib.projectdib.level_generation.BlockDensity.FLOOR;

import java.awt.Point;
import java.util.Random;
import ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType;
import ru.nsu.fit.dib.projectdib.level_generation.Structures.BPGraph;
import ru.nsu.fit.dib.projectdib.level_generation.Level;

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

    graph.nodesList.forEach(area -> {
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
      level.set(FLOOR, fst, snd);
      area.setRoom(fst, snd);
    });
  }

  private void generateMiddleRooms() {
    graph.nodesList.forEach(area -> {
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
      level.set(FLOOR, fst, snd);
      area.setRoom(fst, snd);
    });
  }

  private void generateSmallRooms() {
    graph.nodesList.forEach(area -> {
      if (area.getSizeType() != SizeType.SMALL) {
        return;
      }
      Point fst;
      Point snd;
      fst = new Point(area.getFirstPoint().x + 1 + rn.nextInt(1),
          area.getFirstPoint().y + 1 + rn.nextInt(1));
      snd = new Point(area.getSecondPoint().x - 1 - rn.nextInt(1),
          area.getSecondPoint().y - 1 - rn.nextInt(1));
      level.set(FLOOR, fst, snd);
      area.setRoom(fst, snd);
    });
  }
}
