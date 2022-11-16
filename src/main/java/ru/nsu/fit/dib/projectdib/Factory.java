package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.texture;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
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
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ru.nsu.fit.dib.projectdib.moving.components.BoxMovingComponent;
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
    //Код для тестирования динамической подгрузки объектов
/*    File file = new File("src/test/resources/input.txt");
    Scanner scanner = null;
    try {
      scanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    scanner.useDelimiter("");
    char[][] arr = new char[20][20];
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 20; j++) {
        arr[i][j] = scanner.next().charAt(0);
      }
      scanner.next();
    }
    WallMapper wallMapper = new WallMapper(64, 16, arr);*/

    //////////////
    return entityBuilder()
        .from(data)
        .type(EntityType.PLAYER)
        .bbox(new HitBox(new Point2D(25, 30), BoundingShape.box(150, 200)))
        .with(physics)
        .with(new PlayerMovingComponent())
        //.with(new ChunkLoaderComponent(new ChunkLoader(wallMapper)))
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
        .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        .with(new PhysicsComponent())
        .build();
  }

  /**
   * Entity Box.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Box
   */
  @Spawns("box")
  public Entity newBox(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
    physics.setFixtureDef(new FixtureDef().friction(0.3f));

    return entityBuilder()
            .from(data)
            .type(EntityType.BOX)
            .viewWithBBox(FXGL.texture("box.png", 40, 40))
            .bbox(new HitBox(new Point2D(25, 30), BoundingShape.box(20, 10)))
            .with(physics)
            .with(new BoxMovingComponent())
            .collidable()
            .build();
  }

  /**
   * Entity Chest.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for chest
   */
  @Spawns("chest")
  public Entity newChest(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
    physics.setFixtureDef(new FixtureDef().friction(0.3f));

    return entityBuilder()
            .from(data)
            .type(EntityType.CHEST)
            .viewWithBBox(FXGL.texture("chest.png", 40, 40))
            .bbox(new HitBox(new Point2D(25, 30), BoundingShape.box(20, 10)))
            .with(physics)
            .with(new BoxMovingComponent())
            .collidable()
            .build();
  }

  /**
   * Entity Coin.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Coin
   */
  @Spawns("coin")
  public Entity newCoin(SpawnData data) {
    return entityBuilder()
        .from(data)
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
  @Spawns("arrow")
  public Entity newArrow(SpawnData data) {
    Entity player = FXGLForKtKt.getGameWorld().getSingleton(EntityType.PLAYER);
    Point2D direction = getInput().getMousePositionWorld().subtract(player.getPosition().getX()+40,player.getPosition().getY()+40);
    return entityBuilder()
            .from(data)
            .type(EntityType.ARROW)
            .viewWithBBox("arrow.png")
            .collidable()
            .with(new ProjectileComponent(direction, 300))
            .build();
  }

  /**
   * Entity Bullet.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Bullet
   */
  @Spawns("bullet")
  public Entity newBullet(SpawnData data) {
    Point2D direction = getInput().getMousePositionWorld();
    return entityBuilder()
            .type(EntityType.BULLET)
            .viewWithBBox(new Rectangle(10, 10, Color.BLACK))
            .with(new ProjectileComponent(direction, 400))
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

  @Spawns("button")
  public Entity newButton(SpawnData data) {
    return entityBuilder()
        .from(data)
        .type(EntityType.BUTTON)
        .bbox(
            new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        .with(new CollidableComponent(true))
        .build();
  }

  @Spawns("closedDoor")
  public Entity newClosedDoor(SpawnData data) {
    return entityBuilder()
        .from(data)
        .type(EntityType.CLOSED_DOOR)
        .viewWithBBox(texture("closedDoor.png", 32, 32))
        .with(new PhysicsComponent())
        .build();
  }

  @Spawns("openedDoor")
  public Entity newOpenedDoor(SpawnData data) {
    return entityBuilder()
        .from(data)
        .type(EntityType.OPENED_DOOR)
        .viewWithBBox(texture("openedDoor.png", 32, 32))
        .build();
  }

  @Spawns("doorTrigger")
  public Entity newDoorTrigger(SpawnData data) {
    return entityBuilder()
        .from(data)
        .type(EntityType.DOOR_TRIGGER)
        .bbox(
            new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        .with(new CollidableComponent(true))
        .build();
  }
}
