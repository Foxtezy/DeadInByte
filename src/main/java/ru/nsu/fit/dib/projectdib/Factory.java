package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/** Class Factory for making Entities. */
public class Factory implements EntityFactory {

  /**
   * Entity Player.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Player
   */
  public Entity newPlayer(SpawnData data) {
    return entityBuilder()
        .type(EntityType.PLAYER)
        .viewWithBBox(new Rectangle(30, 30, Color.BLUE))
        .collidable()
        .build();
  }

  /**
   * Entity Box.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Box
   */
  public Entity newBox(SpawnData data) {
    return entityBuilder()
        .type(EntityType.BOX)
        .view(new Rectangle(80, 80, Color.SADDLEBROWN))
        .collidable()
        .build();
  }

  /**
   * Entity Tree.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Tree
   */
  public Entity newTree(SpawnData data) {
    return entityBuilder()
        .type(EntityType.TREE)
        .viewWithBBox(new Rectangle(30, 30, Color.FORESTGREEN))
        .collidable()
        .build();
  }

  /**
   * Entity Coin.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Coin
   */
  public Entity newCoin(SpawnData data) {
    return entityBuilder()
        .type(EntityType.COIN)
        .viewWithBBox(new Circle(5, 5, 5, Color.YELLOW))
        .collidable()
        .build();
  }

  /**
   * Entity Arrow.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Arrow
   */
  public Entity newArrow(SpawnData data) {
    Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
    Point2D direction = getInput().getMousePositionWorld().subtract(player.getCenter());
    return entityBuilder()
        .type(EntityType.ARROW)
        .viewWithBBox(new Rectangle(10, 2, Color.FIREBRICK))
        .collidable()
        .with(new ProjectileComponent(direction, 200))
        .with(new OffscreenCleanComponent())
        .build();
  }

  /**
   * Entity Bullet.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Bullet
   */
  public Entity newBullet(SpawnData data) {
    Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
    Point2D direction = getInput().getMousePositionWorld().subtract(player.getCenter());
    return entityBuilder()
        .type(EntityType.BULLET)
        .viewWithBBox(new Rectangle(3, 3, Color.BLACK))
        .collidable()
        .with(new ProjectileComponent(direction, 1000))
        .with(new OffscreenCleanComponent())
        .build();
  }

  /**
   * Entity Enemy.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Enemy
   */
  public Entity newEnemy(SpawnData data) {
    Circle circle = new Circle(20, 20, 20, Color.RED);
    circle.setStroke(Color.BROWN);
    circle.setStrokeWidth(2.0);
    return entityBuilder()
        .type(EntityType.ENEMY)
        .viewWithBBox(circle)
        .collidable()
        .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 50))
        .build();
  }
}
