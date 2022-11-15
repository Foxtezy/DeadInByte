package ru.nsu.fit.dib.projectdib.moving.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.EntityType;

public class PlayerChaseComponent extends Component {
  private AStarMoveComponent enemy;

  @Override
  public void onUpdate(double tpf) {
    move();
  }

  private void move() {
    var player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
    //var enemy = FXGL.getGameWorld().getSingleton(EntityType.ENEMY);
    int x = player.call("getCellX");
    int y = player.call("getCellY");
    enemy.moveToCell(x, y);
  }
}
