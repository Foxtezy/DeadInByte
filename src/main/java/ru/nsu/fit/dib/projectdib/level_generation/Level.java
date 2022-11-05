package ru.nsu.fit.dib.projectdib.level_generation;

import static java.lang.Math.abs;

import java.awt.Point;

public class Level {
  private int seed;
  private final int width;
  private final int height;
  public int tileType = 20;
  public int[][] map;

  public Level(int seed, int width, int height) {
    this.seed=seed;
    this.width = width;
    this.height = height;
    map = new int[width+1][height+1];
    this.set(new Point(0,0),new Point(width, height));
  }

  /**
   * right up point / left down point of this
   */
  public void set(Point first, Point second) {
    for (int y = first.y; y <= second.y; y++) {
      for (int x = first.x; x <= second.x; x++) {
        map[x][y] = tileType;
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
        if (map[x][y] == 20) c = '#';
        if (map[x][y] == 3) c = '@';
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

}
