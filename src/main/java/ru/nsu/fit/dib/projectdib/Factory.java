package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.geto;
import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGL.texture;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.lengthOfCell;

import com.almasb.fxgl.dsl.EntityBuilder;
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
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.data.ProjectConfig;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.components.BoxMovingComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.Spawner;
import ru.nsu.fit.dib.projectdib.entity.components.*;
import ru.nsu.fit.dib.projectdib.entity.components.control.PlayerControlComponent;
import ru.nsu.fit.dib.projectdib.entity.components.control.ServerControlComponent;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;
import ru.nsu.fit.dib.projectdib.entity.components.enemyAI.AStar;
import ru.nsu.fit.dib.projectdib.entity.components.enemyAI.CellMove;
import ru.nsu.fit.dib.projectdib.entity.components.enemyAI.EnemyAiComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.ShootAttackComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.WeaponInventoryComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.projectile.ProjectileCollisionComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.CreatureViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.EnemyViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HPViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HeroViewComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponViewComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.ui.HPBar;

/** Class Factory for making Entities. */
public class Factory implements EntityFactory {

  public static Entity spawnCreature(
      Creature creature, Point2D position, Integer id, Integer ownerID, Integer seed) {
    SpawnData sd = new SpawnData(position);
    sd.put("creature", creature);
    sd.put("id",id);
    sd.put("owner",ownerID);
    sd.put("seed", seed);
    Entity enemy = Spawner.spawn(creature.getEntityType().getName(), sd);
    enemy.setScaleUniform(0.75);
    return enemy;
  }

  public static Entity spawnWeapon(Weapon weapon, Point2D position, Integer id, Integer ownerID, Integer seed) {
    SpawnData sd = new SpawnData(position);
    sd.put("id", id);
    sd.put("owner", ownerID);
    sd.put("weapon", weapon);
    sd.put("seed", seed);
    Entity entity = Spawner.spawn("weapon", sd);
    entity.setScaleUniform(0.75);
    return entity;
  }

  public EntityBuilder creatureBuilder() {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
    physics.setFixtureDef(new FixtureDef().friction(0.3f));

    return entityBuilder()
        .bbox(new HitBox(new Point2D(30, 220), BoundingShape.box(100, 100)))
        .anchorFromCenter()
        .with(physics)
        .collidable();
  }

  /**
   * Entity Player.
   *
   * @param data contain sets up typical properties such as the position
   * @return entityBuilder for Player
   */
  @Spawns("player")
  public Entity newPlayer(SpawnData data) {
    Creature creature = data.get("creature");
    HeroType type = (HeroType) creature.getType();
    EntityBuilder builder = creatureBuilder();
    // View
    CreatureViewComponent view = new HeroViewComponent(type.getID());
    if (data.get("owner") == MCClient.getClientId()) {
      view.bindDirectionView(
          entity -> getInput().getVectorToMouse(entity.getPosition().add(new Point2D(80, 160))));
      builder.with(new PlayerControlComponent());
    } else {
      view.bindDirectionView(entity -> new Point2D(0, 0));
      builder.with(new ServerControlComponent());
    }
    return builder
        .from(data)
        .type(EntityType.PLAYER)
        .with(view)
        .anchorFromCenter()
        .with(new ShootAttackComponent())
        .with(new CreatureComponent(creature))
        .with(new WeaponInventoryComponent(2))
        .with(new DataComponent(EntityType.PLAYER, data.get("owner"), data.get("id"), data.get("seed")))
        .with(new CellMove(lengthOfCell, lengthOfCell, 250))
        .with(new HealthIntComponent(creature.getMaxHP()))
        .with(new HPViewComponent(creature.getType(), creature.getMaxHP()))
        .build();
  }

  @Spawns("enemy")
  public Entity Enemy(SpawnData data) {
    Creature creature = data.get("creature");
    EnemyType type = (EnemyType) creature.getType();
    EntityBuilder builder = creatureBuilder();
    CreatureViewComponent view = new EnemyViewComponent(type.getID());
    view.bindDirectionView(entity -> new Point2D(0, 0));
    System.out.println(type.getName()+" with ["+"HP: "+creature.getMaxHP()+", defense:"+creature.getArmorCoefficient()+"] spawned");
    if (MCClient.getClientId() == 1) {
      builder
          .from(data)
          .type(EntityType.ENEMY)
          .with(view)
          .anchorFromCenter()
          .with(new ShootAttackComponent())
          .with(new CreatureComponent(creature))
          .with(new ServerControlComponent())
          .with(new WeaponInventoryComponent(1))
          .with(new DataComponent(EntityType.ENEMY, data.get("owner"), data.get("id"), data.get("seed")))
          .with(new HealthIntComponent(creature.getMaxHP()))
          .with(new CellMove(lengthOfCell, lengthOfCell, 250))
          .with(new EnemyAiComponent());
    } else {
      builder
          .from(data)
          .type(EntityType.ENEMY)
          .with(view)
          .anchorFromCenter()
          .with(new ShootAttackComponent())
          .with(new CreatureComponent(creature))
          .with(new ServerControlComponent())
          .with(new WeaponInventoryComponent(1))
          .with(new DataComponent(EntityType.ENEMY, data.get("owner"), data.get("id"), data.get("seed")))
          .with(new HealthIntComponent(creature.getMaxHP()));
    }
    return builder.build();
  }

  @Spawns("weapon")
  public Entity Weapon(SpawnData data) {
    Weapon weapon = data.get("weapon");
    ImageView iv = imageViewFromSpriteSheet(weapon.getType().getSize().getImage(),
        weapon.getType().getId(),
        weapon.getType().getSize().getWidth(),
        weapon.getType().getSize().getHeight(),
        ProjectConfig._WEAPON_COLUMNS);
    WeaponComponent weaponComponent = new WeaponComponent(weapon);
    DataComponent dataComponent = new DataComponent(EntityType.WEAPON, data.get("owner"), data.get("id"), data.get("seed"));
    return entityBuilder(data)
        .from(data)
        .type(EntityType.WEAPON)
        .viewWithBBox(iv)
        .anchorFromCenter()
        .with(new DataAttackComponent())
        .with(new CollidableComponent(true))
        .with(new CellMove(lengthOfCell, lengthOfCell, 0))
        .with(new WeaponViewComponent(
            weapon.getType().getImgRadius(),
            weapon.getType().getRotation(),
            weapon.getRadius(),
            weapon.getDistance()))
        .with(weaponComponent)
        .with(dataComponent)
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
    int ownerID = data.get("owner");
    Point2D direction = data.get("direction");
    int attack = data.get("attack");
    int damage = data.get("damage");
    int id = data.get("id");
    Projectiles projectile = data.get("projectileType");
    //System.out.println(projectile.getName()+" with [attack: "+attack+", damage: "+damage+"] spawned");
    AnimatedTexture texture = new AnimatedTexture(projectile.getAnimation());
    texture.setScaleX(0.75);
    texture.setScaleY(0.75);
    texture.play();
    Entity entity =  entityBuilder()
        .from(data)
        .type(EntityType.PROJECTILE)
        .view(texture)
        .anchorFromCenter()
        .bbox(projectile.getHitbox())
        .with(new ProjectileCollisionComponent())
        .with(new DataAttackComponent(attack,damage))
        .with(new DataComponent(EntityType.PROJECTILE,ownerID,id, 0))
        .with(new ProjectileComponent(direction, projectile.getSpeed()))
        .with(new OffscreenCleanComponent())
        .collidable()
        .build();
    entity.setScaleUniform(0.75);
    return entity;
  }

  /*
  @Spawns("bow")
  public Entity newBow(SpawnData data) {
    return entityBuilder(data)
        .from(data)
        .type(EntityType.BOW)
        .viewWithBBox(texture("red_bow.png", 15, 50))
        .bbox(new HitBox(BoundingShape.box(50, 15)))
        .with(new CollidableComponent(true))
        .build();
  }*/

  ImageView imageViewFromSpriteSheet(Image img, int number, int spriteWidth, int spriteHeight,
      int columns) {
    ImageView iv = new ImageView(img);
    iv.setViewport(
        new Rectangle2D(spriteWidth * (number % columns), spriteHeight * (number / columns),
            spriteWidth, spriteHeight));
    return iv;
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

  @Spawns("explosion")
  public Entity newExplosion(SpawnData data) {
    Image image = image("explosion.png");
    AnimatedTexture texture = new AnimatedTexture(
        new AnimationChannel(image, 4, 960 / 5, 768 / 4, Duration.seconds(0.66),
            0, 3));
    return entityBuilder()
        .from(data)
        .type(EntityType.EXPLOSION)
        .viewWithBBox(texture("explosion.png", 16, 16))
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
        .bbox(new HitBox(BoundingShape.box(32, 32)))
        .with(new CollidableComponent(true))
        .with("openedDoor", data.get("openedDoor"))
        .build();
  }
}
