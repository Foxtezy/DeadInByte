package ru.nsu.fit.dib.projectdib.mapperobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.loaderobjects.Chunk;

public class WallMapper {


  private Map<Chunk, List<PhysicalObject>> walls = new HashMap<>();

  private static final char WALL = 'W';

  private static final char FLOOR = '.';

  private static final char BOUND = '#';

  private char[][] wallMap;

  private int lastId = 0;

  private final int chunkSize;

  private final int tileSize;

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
   */
  public WallMapper(int chunkSize, int tileSize, char[][] map) {
    this.chunkSize = chunkSize;
    this.tileSize = tileSize;
    wallMap = Arrays.copyOf(map, map.length);
    markWalls();
    makeWall();
  }

  public int getChunkSize() {
    return chunkSize;
  }

  public int getTileSize() {
    return tileSize;
  }

  public Map<Chunk, List<PhysicalObject>> getWalls() {
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
    while (wallMap[y][x] == WALL) {
      wallMap[y][x] = BOUND;
      y++;
    }
    PhysicalObject physicalObject = new PhysicalObject(lastId + 1, EntityType.WALL, sx * tileSize, sy * tileSize, tileSize, (y - sy) * tileSize);
    lastId++;
    addGameObject(physicalObject);
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
    while (wallMap[y][x] == WALL) {
      wallMap[y][x] = BOUND;
      x++;
    }
    PhysicalObject physicalObject = new PhysicalObject(lastId + 1, EntityType.WALL, sx * tileSize, sy * tileSize, (x - sx) * tileSize, tileSize);
    lastId++;
    addGameObject(physicalObject);
  }

  private void addGameObject(PhysicalObject physicalObject) {
    Chunk chunk = new Chunk(physicalObject.x() / chunkSize, physicalObject.y() / chunkSize);
    if (!walls.containsKey(chunk)) {
      walls.put(chunk, new ArrayList<>());
    }
    walls.get(chunk).add(physicalObject);
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
