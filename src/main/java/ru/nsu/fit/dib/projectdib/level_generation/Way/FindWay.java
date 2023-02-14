package ru.nsu.fit.dib.projectdib.level_generation.Way;

import static java.lang.Math.abs;
import static ru.nsu.fit.dib.projectdib.level_generation.BlockDensity.WALL;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import ru.nsu.fit.dib.projectdib.level_generation.BlockDensity;
import ru.nsu.fit.dib.projectdib.level_generation.Level;
import ru.nsu.fit.dib.projectdib.level_generation.RoomGeneration.Room;
import ru.nsu.fit.dib.projectdib.level_generation.Structures.BPGraph;

public class FindWay {

  private final Level level;
  private WayPoint start;
  private WayPoint finish;
  private WayPoint[][] wayMap;
  private PriorityQueue<WayPoint> open;
  private PriorityQueue<WayPoint> closed;
  private int hallwayWidth;
  private Room startRoom;
  private Room finishRoom;
  public FindWay(Level level,int wallWeight) {
    this.level = level;

    this.wayMap = new WayPoint[level.getWidth()][level.getHeight()];
    for (int y = 0; y < level.getHeight(); y++) {
      for (int x = 0; x < level.getWidth(); x++) {
        if (level.map[x][y]==WALL.density) this.wayMap[x][y] = new WayPoint(wallWeight);
        else this.wayMap[x][y] = new WayPoint(level.map[x][y]);
        this.wayMap[x][y].x = x;
        this.wayMap[x][y].y = y;
      }
    }
  }

  public WayPoint getFinish() {
    return finish;
  }

  public Level getLevel() {
    return level;
  }

  public void findWay(int hallwayWidth, Room startRoom, Room finishRoom) {
    this.startRoom = startRoom;
    this.finishRoom = finishRoom;
    findWay(hallwayWidth, startRoom.getCentrePoint(), finishRoom.getCentrePoint());
  }

  public void findWay(int hallwayWidth, Point pointStart, Point pointFinish) {
    this.hallwayWidth = hallwayWidth;
    this.start = wayMap[pointStart.x][pointStart.y];
    this.finish = wayMap[pointFinish.x][pointFinish.y];

    open = new PriorityQueue<>();
    closed = new PriorityQueue<>();

    WayPoint node = start;
    WayPoint lastnode = node;

    open.add(node);
    node.weight = 0;
    while (!open.isEmpty()) {

      node = open.element();
      open.remove(node);
      closed.add(node);
      if (node == finish) {
        break;
      }

      int x = node.x;
      int y = node.y;

      addToOpen(node, x + 1, y);
      addToOpen(node, x - 1, y);
      addToOpen(node, x, y + 1);
      addToOpen(node, x, y - 1);

      lastnode = node;
    }
  }

  public void printWay(BlockDensity block) {
    WayPoint node = finish;
    while (true) {
      int x = node.x;
      int y = node.y;
      int wayDensity = block.density;
      level.map[x][y] = wayDensity;
      if (hallwayWidth >= 2) {
        level.map[x][y - 1] = wayDensity;
      }
      if (hallwayWidth >= 2) {
        level.map[x - 1][y] = wayDensity;
      }
      if (hallwayWidth >= 2) {
        level.map[x - 1][y - 1] = wayDensity;
      }

      if (hallwayWidth == 3) {
        level.map[x][y + 1] = wayDensity;
      }
      if (hallwayWidth == 3) {
        level.map[x + 1][y] = wayDensity;
      }
      if (hallwayWidth == 3) {
        level.map[x + 1][y + 1] = wayDensity;
      }
      if (hallwayWidth == 3) {
        level.map[x - 1][y + 1] = wayDensity;
      }
      if (hallwayWidth == 3) {
        level.map[x + 1][y - 1] = wayDensity;
      }
      if (node == start || node.last == null) {
        break;
      }
      node = node.last;
    }
  }

  public List<Room> getRoomList() {
    List<Room> reversedWay = new ArrayList<>();
    WayPoint node = finish;
    while (true) {
      //
      Point point = new Point(node.x, node.y);
      int k = level.graph.nodesList.size();
      for (int i = 0; i < k; i++) {
        if (level.graph.nodesList.get(i).room.equals(point)) {
          if (!reversedWay.contains(level.graph.nodesList.get(i).room)) {
            reversedWay.add(level.graph.nodesList.get(i).room);
          }
          break;
        }
      }
      //
      if (node == start || node.last == null) {
        break;
      }
      node = node.last;
    }

    ArrayList<Room> way = new ArrayList<>();
    for (int i = reversedWay.size() - 1; i >= 0; i--) {
      way.add(reversedWay.get(i));
    }
    return way;
  }

  private void addToOpen(WayPoint node, int x, int y) {
    if (x >= level.getWidth() || x < 0 || y >= level.getHeight() || y < 0) {
      return;
    }
    int enc = 0;
    if (node.last != null && (abs(node.last.x - x) == 2 || abs(node.last.y - y) == 2)) {
      enc = -1;
    }

    //numberNeiboursIsWall
    enc += numberNeighbourWalls(new Point(node.x - hallwayWidth / 2, node.y - hallwayWidth / 2));

    if ((node.x + 1 >= 0 && node.x + 1 < level.getWidth()) &&
        (node.y >= 0 && node.y < level.getHeight()) &&
        level.map[node.x + 1][node.y] == WALL.density) {
      enc += 2;
    }
    if ((node.x - 1 >= 0 && node.x - 1 < level.getWidth()) &&
        (node.y >= 0 && node.y < level.getHeight()) &&
        level.map[node.x - 1][node.y] == WALL.density) {
      enc += 2;
    }
    if ((node.x >= 0 && node.x < level.getWidth()) &&
        (node.y + 1 >= 0 && node.y + 1 < level.getHeight()) &&
        level.map[node.x][node.y + 1] == WALL.density) {
      enc += 2;
    }
    if ((node.x >= 0 && node.x < level.getWidth()) &&
        (node.y - 1 >= 0 && node.y - 1 < level.getHeight()) &&
        level.map[node.x][node.y - 1] == WALL.density) {
      enc += 2;
    }

    if ((!open.contains(wayMap[x][y])) && (!closed.contains(wayMap[x][y]))) {
      wayMap[x][y].manhattanDistance(finish);

      wayMap[x][y].reweight(node, enc);

      open.add(wayMap[x][y]);
    } else {
      if (wayMap[x][y].reweight(node, enc)) {
        if (closed.contains(wayMap[x][y])) {
          closed.remove(wayMap[x][y]);
          closed.add(wayMap[x][y]);
        }
      }
    }
  }

  private int numberNeighbourWalls(Point fst) {
    int k = 0;
    for (int x = 0; x < hallwayWidth; x++) {
      for (int y = 0; y < hallwayWidth; y++) {
        if (fst.x + x >= 0 && fst.x + x < level.getWidth() && fst.y + y >= 0
            && fst.y + y < level.getHeight()) {
          if (level.map[fst.x + x][fst.y + y] == WALL.density) {
            k++;
          }
        } else {
          k++;
        }
      }
    }
    return k;
  }

  public Room getStartRoom() {
    return startRoom;
  }

  public Room getFinishRoom() {
    return finishRoom;
  }
}
