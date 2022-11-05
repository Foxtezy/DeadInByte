package ru.nsu.fit.dib.projectdib.mapperobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameObjectMapper {

  private List<GameObject> walls;

  private static final char WALL = 'W';

  private static final char FLOOR = '.';

  private static final char BOUND = '#';

  private char[][] wallMap;

  private int lastId = 0;

  /**
   * Makes list of entities (walls).
   *
   * @param map generated map (with bounds on the edges)
   *             _ _ _ _ _
   *            |* * * * *|
   *            |*       *|
   *            |*       *|
   *            |* * * * *|
   *            |_ _ _ _ _|
   *            EDGES ARE NOT WALLS!!!!!
   * @return list of walls.
   */
  public List<GameObject> makeWalls(char[][] map) {
    walls = new ArrayList<>();
    wallMap = Arrays.copyOf(map, map.length);
    markWalls();
    makeWall();
    return walls;
  }

  /**
   * Method public for testing.
   *
   * @return map.
   */
  public char[][] markWalls() {
    for (int i = 1; i < wallMap.length - 1; i++) {
      for (int j = 1; j < wallMap[i].length - 1; j++) {
        if (isWall(i, j)) {
          wallMap[i][j] = WALL;
        }
      }
    }
    return wallMap;
  }

  private void makeWall() {
    for (int y = 1; y < wallMap.length - 1; y++) {
      for (int x = 1; x < wallMap[y].length - 1; x++) {
        if (isWall(y, x)) {
          wallMap[y][x] = BOUND;
          makeEntity(y, x);
        }
      }
    }
  }

  private void makeEntityDown(int sy, int sx) {
    int y = sy + 1;
    int x = sx;
    while (wallMap[y][x] != BOUND) {
      wallMap[y][x] = BOUND;
      y++;
    }
    GameObject gameObject = new GameObject(lastId + 1, "wall", sx, sy, 1, y - sy);
    lastId++;
    walls.add(gameObject);
  }

  private void makeEntity(int sy, int sx) {
    if (wallMap[sy][sx + 1] == WALL) {
      makeEntityRight(sy, sx);
    }
    if (wallMap[sy + 1][sx] == WALL) {
      makeEntityDown(sy, sx);
    }
  }

  private void makeEntityRight(int sy, int sx) {
    int y = sy;
    int x = sx + 1;
    while (wallMap[y][x] != BOUND) {
      wallMap[y][x] = BOUND;
      x++;
    }
    GameObject gameObject = new GameObject(lastId + 1, "wall", sx, sy, x - sx, 1);
    lastId++;
    walls.add(gameObject);
  }

  private boolean isWall(int y, int x) {
    if (wallMap[y][x] == FLOOR) {
      return false;
    }
    if (wallMap[y + 1][x] != FLOOR && wallMap[y][x + 1] != FLOOR && wallMap[y - 1][x] != FLOOR
        && wallMap[y][x - 1] != FLOOR) {
      return false;
    }
    return true;
  }
}
