package ru.nsu.fit.dib.projectdib.entity.components;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.set;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarPathfinder;
import com.almasb.fxgl.physics.CollisionHandler;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.Factory;

/**
 * Описывает движение..врагов? Пояснить.
 */
public class PlayerChaseComponent extends Component {
    private int oldX = 0;
    private int oldY = 0;

  private final AStarMoveComponent enemy;
//  private static AStarPathfinder enemyPath = new AStarPathfinder(
//      AStarGrid.fromWorld(FXGL.getGameWorld(), FXGLForKtKt.getAppWidth(), getAppHeight(), 250, 250,
//          (type) -> {
//            if (type == EntityType.WALL || type == EntityType.CLOSED_DOOR) {
//              return CellState.NOT_WALKABLE;
//            }
//            return CellState.WALKABLE;
//          }));
public PlayerChaseComponent(AStarMoveComponent enemy) {
    this.enemy = enemy;
}


    @Override
  public void onUpdate(double tpf) {
    move();
  }

  private void move() {
    Entity player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
    //var enemy = FXGL.getGameWorld().getSingleton(EntityType.ENEMY);
    int x = player.call("getCellX");
    int y = player.call("getCellY");
    //enemy.getGrid().
   if (player.isColliding(enemy.getEntity())) {
      enemy.stopMovement();
      enemy.getEntity().getComponent(EnemyComponent.class).setNewPosition(0,false);
   } else {
      enemy.moveToCell(x, y);
      enemy.getEntity().getComponent(EnemyComponent.class).setNewPosition(x - oldX, enemy.isMoving());

      oldX = x;
      oldY = y;
    }
}
}
