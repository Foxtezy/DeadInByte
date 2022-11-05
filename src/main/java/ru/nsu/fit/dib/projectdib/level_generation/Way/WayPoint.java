package ru.nsu.fit.dib.projectdib.level_generation.Way;


import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import org.jetbrains.annotations.NotNull;

public class WayPoint implements Comparable<WayPoint> {
  public int x=0;
  public int y=0;
  public int weight=2000000;
  public int distance=0;
  public WayPoint last;

  @Override
  public int compareTo(@NotNull WayPoint o) {

    return (this.weight+this.distance)-(((WayPoint)o).weight+((WayPoint)o).distance);
  }
  private int euclideanDistance(WayPoint a)
  {
    return (int) sqrt((a.x-this.x)*(a.x-this.x) + (a.y-this.y)*(a.y-this.y));
  }
  public void manhattanDistance(WayPoint a)
  {
    distance = abs(a.x-this.x) + abs(a.y-this.y);
  }
  public boolean reweight(int map, WayPoint node)
  {
    if (this.weight>node.weight+map)
    {
      this.last=node;
      this.weight = node.weight+map;
      return true;
    }
    return false;
  }
}
