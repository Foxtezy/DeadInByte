package ru.nsu.fit.dib.projectdib.loaderobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chunk {
  private final int x;
  private final int y;

  public Chunk(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Chunk chunk)) {
      return false;
    }
    return getX() == chunk.getX() && getY() == chunk.getY();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY());
  }

  public boolean isNeighboringChunk(Chunk chunk) {
    if (equals(chunk)) {
      return true;
    }
    if (Math.abs(chunk.getX() - x) <= 1 && Math.abs(chunk.getY() - y) <= 1) {
      return true;
    }
    return false;
  }

  public List<Chunk> neighboringChunks() {
    List<Chunk> neighborList = new ArrayList<>();
    neighborList.add(this);
    neighborList.add(new Chunk(x-1, y-1));
    neighborList.add(new Chunk(x-1, y));
    neighborList.add(new Chunk(x, y-1));
    neighborList.add(new Chunk(x+1, y));
    neighborList.add(new Chunk(x, y+1));
    neighborList.add(new Chunk(x+1, y+1));
    neighborList.add(new Chunk(x-1, y+1));
    neighborList.add(new Chunk(x+1, y-1));
    return neighborList;
  }

  @Override
  public String toString() {
    return "Chunk{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
