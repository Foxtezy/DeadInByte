package ru.nsu.fit.dib.projectdib.entity.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

/**
 * Описывает движение..врагов? Пояснить.
 */
public class PlayerChaseComponent extends Component {

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

  // @Override
  // public void onUpdate(double tpf) {
  //   move();
  //}

  public void move(Entity entityTarget) {
//    var player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
//    //var enemy = FXGL.getGameWorld().getSingleton(EntityType.ENEMY);
//    int x = player.call("getCellX");
//    int y = player.call("getCellY");
//   if (player.isColliding(enemy.getEntity())) {
//      enemy.stopMovement();
//    } else {
//      enemy.moveToCell(x, y);
//    }
    int x = entityTarget.call("getCellX");
    int y = entityTarget.call("getCellY");
    enemy.moveToCell(x, y);
  }

  public void stop() {
    enemy.stopMovement();
  }

  public int pathLength(Entity entityTarget) {
    int x = entityTarget.call("getCellX");
    int y = entityTarget.call("getCellY");
    return enemy.getGrid().get(x, y).getFCost();
  }
}
