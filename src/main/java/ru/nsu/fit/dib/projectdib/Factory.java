package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.set;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;

import static java.lang.Character.getName;
import static java.lang.Character.toUpperCase;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarPathfinder;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import ru.nsu.fit.dib.projectdib.data.HeroSpecs;
import ru.nsu.fit.dib.projectdib.moving.components.PlayerChaseComponent;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
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
    //HeroSpecs specs = new HeroSpecs("1", "shotgun", "ak",10, 250.0, "player.png");

    return entityBuilder()
        .from(data)
        .type(EntityType.PLAYER)
       //.viewWithBBox(texture("weapon_" + playerMovingComponent.getCurrentWeapon()  + ".png", 150,200))
        .bbox(new HitBox(new Point2D(25, 110), BoundingShape.box(160, 160)))
        .anchorFromCenter()
        .with(physics)
        .with(new PlayerMovingComponent(data.get("specification")))
        .with(new CellMoveComponent(25, 25, 250))
        .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
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
        .collidable()
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
    var hp = new HealthIntComponent(3);
    var hpView = new ProgressBar(false);
    hpView.setFill(Color.LIGHTGREEN);
    hpView.setMaxValue(3);
    hpView.setWidth(40);
    hpView.setTranslateY(-10);
    hpView.currentValueProperty().bind(hp.valueProperty());

    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
    physics.setFixtureDef(new FixtureDef().friction(0.3f));

    return entityBuilder()
            .from(data)
            .type(EntityType.BOX)
            .viewWithBBox(FXGL.texture("box.png", 40, 40))
            .bbox(new HitBox(new Point2D(25, 30), BoundingShape.box(20, 10)))
            .view(hpView)
            .with(hp)
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
    var hp = new HealthIntComponent(3);
    var hpView = new ProgressBar(false);
    hpView.setFill(Color.LIGHTGREEN);
    hpView.setMaxValue(3);
    hpView.setWidth(40);
    hpView.setTranslateY(-10);
    hpView.currentValueProperty().bind(hp.valueProperty());

    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
    physics.setFixtureDef(new FixtureDef().friction(0.3f));

    return entityBuilder()
            .from(data)
            .type(EntityType.CHEST)
            .viewWithBBox(FXGL.texture("chest.png", 40, 40))
            .view(hpView)
            .with(hp)
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
  @Spawns("projectile")
  public Entity newProjectile(SpawnData data) {
    Entity player = FXGLForKtKt.getGameWorld().getSingleton(EntityType.PLAYER);
    Point2D direction = getInput().getMousePositionWorld().subtract(new Point2D(data.get("vert_deflection"),data.get("vert_deflection"))).subtract(player.getCenter().subtract(new Point2D(60,90)));
    Projectiles projectile = data.get("typeProj");
    return entityBuilder()
        .from(data)
        .type(EntityType.PROJECTILE)
        .viewWithBBox(texture("projectile_" + projectile.getName() + ".png", 40, 15))
        .with(new ProjectileComponent(direction, projectile.getSpeed()))
        .with(new OffscreenCleanComponent())
        .collidable()
        .build();
  }
  @Spawns("bow")
  public Entity newBow(SpawnData data) {
    return entityBuilder(data)
        .from(data)
        .type(EntityType.BOW)
        .viewWithBBox(texture("red_bow.png", 15, 50))
        .bbox(new HitBox(BoundingShape.box(50,15)))
        .with(new CollidableComponent(true))
        .build();
  }

  @Spawns("ak")
  public Entity newAK(SpawnData data) {
    return entityBuilder(data)
            .from(data)
            .type(EntityType.AK)
            .viewWithBBox(texture("weapon_ak.png", 75, 20))
            .bbox(new HitBox(BoundingShape.box(75,20)))
            .with(new CollidableComponent(true))
            .build();
  }

  @Spawns("gun")
  public Entity newGun(SpawnData data) {
    return entityBuilder(data)
            .from(data)
            .type(EntityType.GUN)
            .viewWithBBox(texture("gun.png", 25, 20))
            .bbox(new HitBox(BoundingShape.box(25,20)))
            .with(new CollidableComponent(true))
            .build();
  }
  @Spawns("shotgun")
  public Entity newShotgun(SpawnData data) {
    return entityBuilder(data)
            .from(data)
            .type(EntityType.SHOTGUN)
            .viewWithBBox(texture("shotgun.png", 80, 50))
            .bbox(new HitBox(BoundingShape.box(80,50)))
            .with(new CollidableComponent(true))
            .build();
  }

  /**
   * Entity Enemy.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Enemy
   */
  @Spawns("enemy")
  public Entity newEnemy(SpawnData data) {
    Circle circle = new Circle(10, 10, 10, Color.RED);
    circle.setStroke(Color.BROWN);
    circle.setStrokeWidth(2.0);
    return entityBuilder()
        .from(data)
        .type(EntityType.ENEMY)
        .viewWithBBox(circle)
        .anchorFromCenter()
        .collidable()
        .with(new CellMoveComponent(25,25,100))
        .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
        .with(new PlayerChaseComponent())
        .build();
  }

  @Spawns("button")
  public Entity newButton(SpawnData data) {
    return entityBuilder()
        .from(data)
        .type(EntityType.BUTTON)
        .viewWithBBox(texture("button.png", 16, 16))
        .with(new CollidableComponent(true))
        .with("closedDoor", data.get("closedDoor"))
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
            new HitBox(BoundingShape.box(32, 32)))
        .with(new CollidableComponent(true))
        .with("openedDoor", data.get("openedDoor"))
        .build();
  }
}
