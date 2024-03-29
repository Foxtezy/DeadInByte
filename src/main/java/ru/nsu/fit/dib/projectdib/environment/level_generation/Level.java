package ru.nsu.fit.dib.projectdib.environment.level_generation;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import ru.nsu.fit.dib.projectdib.environment.level_generation.AreaGeneration.BinaryPartitionAG;
import ru.nsu.fit.dib.projectdib.environment.level_generation.RoomGeneration.EmptyRoomGenerator;
import ru.nsu.fit.dib.projectdib.environment.level_generation.RoomGeneration.Room;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.BPLeaf;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.SizeType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Edge;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.BPGraph;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Way.FindWay;

/**
 * Основной класс генератора. Содержит в себе все объекты для генерации уровня.
 *
 */
public class Level implements Serializable {

  public final Random rn;
  public final BPGraph graph;
  private final int seed;
  private final int width;
  private final int height;
  private final BPLeaf tree;
  public List<Room> roomList;
  public Room start;
  public Room finish;
  public int[][] map;

  public Level(int seed, int width, int height, int numberOfBigAreas, int numberOfMiddleAreas) {
    this.seed = seed;
    this.width = width;
    this.height = height;
    map = new int[width + 1][height + 1];
    this.rn = new Random(seed);

    this.set(BlockDensity.WALL, new Point(0, 0), new Point(width, height));
    //строим дерево
    BinaryPartitionAG partition = new BinaryPartitionAG(this, numberOfBigAreas,
        numberOfMiddleAreas);

    tree = new BPLeaf(new Point(0, 0), new Point(64, 64));
    partition.setPartition(tree);

    //граф
    graph = new BPGraph(tree, this);
    //Рисуем комнаты и коридоры
    EmptyRoomGenerator generator = new EmptyRoomGenerator(graph, this);
    generator.generateRoom(SizeType.BIG);
    graph.addEdges(SizeType.BIG, rn);
    generateHallways(3);
    generator.generateRoom(SizeType.MIDDLE);
    graph.addEdges(SizeType.MIDDLE, rn);
    generateHallways(2);
    generator.generateRoom(SizeType.SMALL);
    graph.addEdges(SizeType.SMALL, rn);
    generateHallways(1);
    //проверка на возможность входа во все комнаты и выбор старта и финиша + список комнат,
    // которые посетит игрок при минимальном пути.
    checkingEntrance();
    print();
    //Здесь будет вызываться RoomGenerator
  }
  private void checkingEntrance() {
    FindWay best = null;

    int k = graph.getAreaList().size();
    for (int x = 0; x < k; x++) {
      for (int y = x+1; y < k; y++) {
        FindWay finder = new FindWay(this,20000);
        finder.findWay(1, graph.getAreaList().get(x).getRoom(), graph.getAreaList().get(y).getRoom());
        finder.printWay(BlockDensity.FLOOR);

        if (best == null || finder.getFinish().wayLength > best.getFinish().wayLength) {
          best = finder;
        }
      }
    }
    best.printWay(BlockDensity.WAY);
    start = best.getFinishRoom();
    finish = best.getFinishRoom();
    roomList = best.getRoomList();
  }

  private void generateHallways(int hallwayWidth) {
    int k = graph.getEdgeStack().size();
    for (int i = 0; i < k; i++) {
      Edge edge = graph.getEdgeStack().pop();
      FindWay finder;
      if (edge.getArea(0).getSizeType()== SizeType.SMALL && edge.getArea(1).getSizeType()== SizeType.SMALL) {
        finder = new FindWay(this, 50);
      }
      else {
        finder = new FindWay(this, 500);
      }
      if (edge.getArea(0).getRoom().getWeight() >= edge.getArea(1).getRoom().getWeight()) {
        finder.findWay(hallwayWidth, edge.getArea(0).getRoom().getCentrePoint(),
            edge.getArea(1).getRoom().getCentrePoint());
      } else {
        finder.findWay(hallwayWidth, edge.getArea(1).getRoom().getCentrePoint(),
            edge.getArea(0).getRoom().getCentrePoint());
      }
      finder.printWay(BlockDensity.FLOOR);
      graph.getEdgesList().add(edge);
    }
  }

  /**
   * right up point / left down point of this
   */
  public void set(BlockDensity density, Point first, Point second) {
    for (int y = first.y; y <= second.y; y++) {
      for (int x = first.x; x <= second.x; x++) {
        map[x][y] = density.density;
      }
    }
  }

  /**
   * print to console
   */
  public void print() {
    for (int y = 0; y <= height; y++) {
      for (int x = 0; x <= width; x++) {
        char c = '.';
        if (start.getCentrePoint().x==x && start.getCentrePoint().y==y || finish.getCentrePoint().x==x && finish.getCentrePoint().y==y){
          c='O';
        }
        else
        {
          if (map[x][y] == BlockDensity.WALL.density) {
            c = '#';
          }
          if (map[x][y] == 3) {
            c = '@';
          }
        }
        System.out.printf("%2c", c);
      }
      System.out.println();
    }
  }

  /**
   * change Tile types to Tile id
   */
  public void toTileId(String paletteName) {
    JS0NHashMap palette = new JS0NHashMap(paletteName);
    palette.deserializeHashMap();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (map[x][y] == 1) {
          map[x][y] = palette.get("platform");
        } else {
          map[x][y] = palette.get("wall");
        }

      }
    }
  }

  public int getSeed() {
    return seed;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
