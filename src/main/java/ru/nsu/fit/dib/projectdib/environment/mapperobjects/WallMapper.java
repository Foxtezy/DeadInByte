package ru.nsu.fit.dib.projectdib.environment.mapperobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.scene.control.Cell;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.BlockDensity;
import ru.nsu.fit.dib.projectdib.environment.loaderobjects.Chunk;

/**
 * Создает colliders(physical walls).
 */
public class WallMapper {

  public int[][] nonInvertedMap;
  private static final int WALL = BlockDensity.WALL.density + 1;
  private static final int FLOOR = BlockDensity.FLOOR.density;
  private static final int BOUND = BlockDensity.WALL.density;
  private final int chunkSize;
  private final int tileSize;
  private Map<Chunk, List<PhysicalObject>> walls = new HashMap<>();
  private int[][] wallMap;
  private int lastId = 0;
  private AStarGrid grid;
  /**
   * Makes list of entities (walls).
   *
   * @param map generated map (with bounds on the edges) _ _ _ _ _ |* * * * *| |*       *| |* *| |*
   *            * * * *| |_ _ _ _ _| EDGES ARE NOT WALLS!!!!!
   */
  public WallMapper(int chunkSize, int tileSize, int[][] map, AStarGrid grid) {
    this.grid = grid;
    this.nonInvertedMap = map;
    this.chunkSize = chunkSize;
    this.tileSize = tileSize;
    wallMap = invert(map);
    markWalls();
    makeWall();

  }

  private int[][] invert(int[][] map) {
    int[][] newMap = new int[map.length][map.length];
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[x].length; y++) {
        newMap[x][y] = map[y][x];
      }
    }
    return newMap;
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
  public int[][] markWalls() {
    for (int i = 0; i < wallMap.length; i++) {
      for (int j = 0; j < wallMap[i].length; j++) {
        if (isWall(i, j)) {
          wallMap[i][j] = WALL;
  //        grid.set(i,j, new AStarCell(i,j, CellState.NOT_WALKABLE));
        }
      }
    }
    return wallMap;
  }

  private void makeWall() {
    for (int y = 0; y < wallMap.length; y++) {
      for (int x = 0; x < wallMap[y].length; x++) {
        if (wallMap[y][x] == WALL) {
          wallMap[y][x] = BOUND;
          makeEntity(y, x);
        }
      }
    }
    int x = 10;
  }

  private void makeEntityDown(int sy, int sx) {
    int y = sy + 1;
    int x = sx;
    while (y < wallMap.length && wallMap[y][x] == WALL) {
      wallMap[y][x] = BOUND;
      y++;
    }
    PhysicalObject physicalObject = new PhysicalObject(lastId + 1, EntityType.WALL, sx * tileSize,
        sy * tileSize, tileSize, (y - sy) * tileSize);
    lastId++;
    addGameObject(physicalObject);
  }

  private void makeEntity(int sy, int sx) {
    if (sx + 1 < wallMap[sy].length && wallMap[sy][sx + 1] == WALL) {
      makeEntityRight(sy, sx);
    } else if (sy + 1 < wallMap.length && wallMap[sy + 1][sx] == WALL) {
      makeEntityDown(sy, sx);
    } else {
      makeEntityRight(sy, sx);
    }
  }

  private void makeEntityRight(int sy, int sx) {
    int y = sy;
    int x = sx + 1;
    while (x < wallMap[y].length && wallMap[y][x] == WALL) {
      wallMap[y][x] = BOUND;
      x++;
    }
    PhysicalObject physicalObject = new PhysicalObject(lastId + 1, EntityType.WALL, sx * tileSize,
        sy * tileSize, (x - sx) * tileSize, tileSize);
    lastId++;
    addGameObject(physicalObject);
  }

  private void addGameObject(PhysicalObject physicalObject) {
    Chunk chunk = new Chunk(physicalObject.x() / chunkSize, physicalObject.y() / chunkSize);
    if (!walls.containsKey(chunk)) {
      walls.put(chunk, new ArrayList<>());
    }
    walls.get(chunk).add(physicalObject);
    for (int c = 1; ; c++) {
      if (physicalObject.x() + c * chunkSize >= physicalObject.width()) {
        break;
      }
      Chunk chunkw = new Chunk((physicalObject.x() / chunkSize) + c,
          physicalObject.y() / chunkSize);
      if (!walls.containsKey(chunkw)) {
        walls.put(chunkw, new ArrayList<>());
      }
      walls.get(chunkw).add(physicalObject);
    }

    for (int c = 1; ; c++) {
      if (physicalObject.y() + c * chunkSize >= physicalObject.height()) {
        break;
      }
      Chunk chunkh = new Chunk(physicalObject.x() / chunkSize,
          (physicalObject.y() / chunkSize) + c);
      if (!walls.containsKey(chunkh)) {
        walls.put(chunkh, new ArrayList<>());
      }
      walls.get(chunkh).add(physicalObject);
    }

  }


  private boolean isWall(int y, int x) {
    if (wallMap[y][x] <= FLOOR) {
      return false;
    }
    try {
      if (wallMap[y + 1][x] > FLOOR && wallMap[y][x + 1] > FLOOR && wallMap[y - 1][x] > FLOOR
          && wallMap[y][x - 1] > FLOOR) {
        return false;
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      return true;
    }

    return true;
  }
}
