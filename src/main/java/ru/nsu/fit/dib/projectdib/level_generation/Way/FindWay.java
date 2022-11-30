package ru.nsu.fit.dib.projectdib.level_generation.Way;
import static java.lang.Math.abs;

import java.awt.Point;
import java.util.PriorityQueue;
import ru.nsu.fit.dib.projectdib.level_generation.Level;

public class FindWay {

  private final Level level;
  private WayPoint start;
  private WayPoint finish;
  private WayPoint[][] wayMap;
  private PriorityQueue<WayPoint> open;
  private PriorityQueue<WayPoint> closed;

  public FindWay(Level level) {
    this.level=level;
    this.wayMap = new WayPoint[level.getWidth()][level.getHeight()];
    for (int y = 0; y < level.getHeight(); y++) {
      for (int x = 0; x < level.getWidth(); x++) {
        this.wayMap[x][y] = new WayPoint();
        this.wayMap[x][y].x = x;
        this.wayMap[x][y].y = y;
      }
    }
  }

  public void findWay(Point pointStart, Point pointFinish) {
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

  public void printWay() {
    WayPoint node = finish;
    while (true) {
      int x = node.x;
      int y = node.y;
      level.map[x][y] = 3;
      if (node == start) {
        break;
      }
      node = node.last;
    }
  }

  private void addToOpen(WayPoint node, int x, int y) {
    if (x >= level.getWidth() || x < 0 || y >= level.getHeight() || y < 0) {
      return;
    }
    int enc = 0;
    if (node.last!=null && (abs(node.last.x - x)==2 || abs(node.last.y-y)==2)) enc=-1;
    if ((!open.contains(wayMap[x][y])) && (!closed.contains(wayMap[x][y]))) {
      wayMap[x][y].manhattanDistance(finish);

      wayMap[x][y].reweight(level.map[x][y], node,enc);

      open.add(wayMap[x][y]);
    } else {
      if (wayMap[x][y].reweight(level.map[x][y], node,enc)) {
        if (closed.contains(wayMap[x][y])) {
          closed.remove(wayMap[x][y]);
          closed.add(wayMap[x][y]);
        }
      }
    }
  }
}
