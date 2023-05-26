package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.set;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.lengthOfCell;

import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import ru.nsu.fit.dib.projectdib.environment.level_generation.BlockDensity;

public class GridCreator {

  int[][] map;

  public GridCreator(int[][] map) {
    this.map = map;
  }

  public void run() {
    AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), 66, 66, lengthOfCell, lengthOfCell,
        entityType -> CellState.NOT_WALKABLE);
    for (int x = 0; x < map.length; x++) {
      for (int y = 1; y < map[x].length; y++) {
        if (map[x][y] != BlockDensity.WALL.density) {
          grid.set(x, y - 1, new AStarCell(x, y - 1, CellState.WALKABLE));
        }

      }
      set("grid", grid);
    }
  }
}
