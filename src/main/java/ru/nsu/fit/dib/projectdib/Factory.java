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
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import java.lang.module.ModuleFinder;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ru.nsu.fit.dib.projectdib.moving.components.PlayerMovingComponent;

/**
 * Class Factory for making Entities.
 */
public class Factory implements EntityFactory {

  /**
   * Entity Player.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Player
   */
  @Spawns("player")
  public Entity newPlayer(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
    physics.setFixtureDef(new FixtureDef().friction(0.3f));
    return entityBuilder()
        .from(data)
        .type(EntityType.PLAYER)
        .bbox(new HitBox(new Point2D(25,30), BoundingShape.box(150, 200)))
        .with(physics)
        .with(new PlayerMovingComponent())
        .collidable()
        .build();
  }

  @Spawns("platform")
  public Entity platform(SpawnData data) {
    return entityBuilder(data)
        .from(data)
        .type(EntityType.PLATFORM)
        .bbox(
            new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        .with(new CollidableComponent(true))
        .build();
  }

  @Spawns("wall")
  public Entity wall(SpawnData data) {
    return entityBuilder(data)
        .from(data)
        .type(EntityType.WALL)
        .bbox(
            new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        .with(new PhysicsComponent())
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
