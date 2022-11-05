package ru.nsu.fit.dib.projectdib.level_generation.Way;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import com.almasb.fxgl.core.collection.Array;
import java.awt.Point;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class FindWay {

  int width;
  int height;
  WayPoint start;
  WayPoint finish;
  int[][] map;
  WayPoint[][] wayMap;
  PriorityQueue<WayPoint> open;
  PriorityQueue<WayPoint> closed;
  public FindWay(int[][] map, int width, int height) {
    this.width = width;
    this.height = height;
    this.map = map;
    this.wayMap = new WayPoint[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
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
    WayPoint lastnode=node;

    open.add(node);
    node.weight=0;
    while (!open.isEmpty()) {

      node = open.element();
      open.remove(node);
      closed.add(node);
      if (node == finish) break;

      int x = node.x;
      int y = node.y;

      addToOpen(node,x+1,y);
      addToOpen(node,x-1,y);
      addToOpen(node,x,y+1);
      addToOpen(node,x,y-1);

      lastnode = node;
    }
  }
  public void printWay()
  {
    WayPoint node=finish;
    while (true){
      int x = node.x;
      int y = node.y;
      map[x][y] = 3;
      if (node == start) break;
      node = node.last;
    }
  }
  private void addToOpen(WayPoint node, int x,int y)
  {
    if (x>=width || x<0 || y>=height || y<0) return;
    if ((!open.contains(wayMap[x][y])) && (!closed.contains(wayMap[x][y]))) {
      wayMap[x][y].manhattanDistance(finish);
      wayMap[x][y].reweight(map[x][y],node);

      open.add(wayMap[x][y]);
    }
    else
    {
      if (wayMap[x][y].reweight(map[x][y],node)) {
        if (closed.contains(wayMap[x][y])) {
          closed.remove(wayMap[x][y]);
          closed.add(wayMap[x][y]);
        }
      }
    }
  }
}
