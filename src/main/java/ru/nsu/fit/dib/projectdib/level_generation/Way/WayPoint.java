package ru.nsu.fit.dib.projectdib.level_generation.Way;

import static java.lang.Math.abs;

import org.jetbrains.annotations.NotNull;

public class WayPoint implements Comparable<WayPoint> {

  public int x = 0;
  public int y = 0;
  public double weight = 20000;
  public int distance = 0;
  public int wayLength = 0;
  public WayPoint last;

  public WayPoint() {
  }

  @Override
  public int compareTo(@NotNull WayPoint o) {

    return (int) ((this.weight + this.distance) - (((WayPoint) o).weight
        + ((WayPoint) o).distance));
  }

  public void manhattanDistance(WayPoint a) {
    distance = abs(a.x - this.x) + abs(a.y - this.y);
  }

  public boolean reweight(int map, WayPoint node, int encouragement) {
    wayLength = node.wayLength + 1;
    if (this.weight > node.weight + map + encouragement) {
      this.last = node;
      this.weight = node.weight + map + encouragement;
      return true;
    }
    return false;
  }
}
