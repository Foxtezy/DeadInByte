package ru.nsu.fit.dib.projectdib.environment.level_generation.Way;

import static java.lang.Math.abs;

import org.jetbrains.annotations.NotNull;

public class WayPoint implements Comparable<WayPoint> {

  public int x = 0;
  public int y = 0;
  public double weight = 2000000;
  public int density;
  public int distance = 0;
  public int wayLength = 0;
  public WayPoint last;

  public WayPoint(int density) {
    this.density=density;
  }

  @Override
  public int compareTo(@NotNull WayPoint o) {

    return (int) ((this.weight + this.distance) - (((WayPoint) o).weight
        + ((WayPoint) o).distance));
  }

  public void manhattanDistance(WayPoint a) {
    distance = abs(a.x - this.x) + abs(a.y - this.y);
  }

  public boolean reweight(WayPoint node, int encouragement) {
    wayLength = node.wayLength + 1;
    if (this.weight > node.weight + density + encouragement) {
      this.last = node;
      this.weight = node.weight + density + encouragement;
      return true;
    }
    return false;
  }
}
