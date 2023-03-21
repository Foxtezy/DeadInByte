package ru.nsu.fit.dib.projectdib.entity.components;


import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGL.newLocalTimer;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import ru.nsu.fit.dib.projectdib.EntityType;

import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.HPModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.JFXModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.DamageModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.SquareDamageModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TimingsModule;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_height;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_numberColumns;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_width;

/**
 * Описывает движение игрока.
 */
public class PlayerComponent extends CreatureComponent implements Moving {

  private final double scale = 0.07;
  AnimationChannel animationMovement;
  AnimationChannel animationStanding;
  AnimationChannel animationWaiting;
  AnimationChannel animationBack;
  Point2D localAnchor;
  boolean stateChanged = true;
  private PhysicsComponent physics;
  //public String currentWeapon;
  private AnimatedTexture texture;
  private LocalTimer shootTimer = newLocalTimer();
  //  private final double hero.getSpeed( = 250;
  private Creature hero;
  private boolean isMoving = false;

  public PlayerComponent(Creature hero, Point2D localAnchor) {
    this.localAnchor = localAnchor;
    this.hero = hero;
    int heroNumber = hero.getModule(JFXModule.class).getImageID();
    //animation settings
    Image img = new Image(_player);
    animationMovement = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(600),
        5 + heroNumber * 13, 8 + heroNumber * 13);
    animationStanding = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(300), heroNumber * 13,
        1 + heroNumber * 13);
    animationWaiting = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(450),
        2 + heroNumber * 13, 4 + heroNumber * 13);
    animationBack = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(600),
        9 + heroNumber * 13, 12 + heroNumber * 13);
    texture = new AnimatedTexture(animationStanding);
    texture.loop();
    texture.setPreserveRatio(true);
  }

  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(new Point2D(15, 20));
    entity.getViewComponent().addChild(texture);
    entity.setScaleUniform(scale);
    //setCurrentWeapon("bow");
  }

  @Override
  public void onUpdate(double tpf) {
    if (isMoving && !isMoving() || !isMoving && isMoving()) {
      stateChanged = true;
      isMoving = isMoving();
    }
    //Изменилось ли состояние
    if (stateChanged) {
      //texture.loopAnimationChannel(isMoving() ? animationMovement : animationStanding);
      AnimationChannel animation = texture.getAnimationChannel();
      //Движется или нет
      if (isMoving()) {
        texture.loopAnimationChannel(animationMovement);
      } else {
        texture.loopAnimationChannel(animationStanding);
      }
      stateChanged = false;
    }

    Point2D mouseVelocity = getDirectionView();
    //Поворот
    if (mouseVelocity.angle(1, 0) <= 90) {
      rotate(SIDE.RIGHT);
    } else {
      rotate(SIDE.LEFT);
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }

  public void rotate(SIDE side) {
    if (side == SIDE.RIGHT) {
      texture.setScaleX(1);
    } else {
      texture.setScaleX(-1);
    }
  }

  private boolean isMoving() {
    return FXGLMath.abs(physics.getVelocityX()) > 0 || FXGLMath.abs(physics.getVelocityY()) > 0;
  }

  @Override
  public void left() {
    physics.setVelocityX(-hero.getSpeed());
    //physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(hero.getSpeed()));
  }

  @Override
  public void right() {
    physics.setVelocityX(hero.getSpeed());
    //physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(hero.getSpeed()));
  }

  @Override
  public void up() {
    //да, тут должен быть именно минус
    physics.setVelocityY(-hero.getSpeed());
    //physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(hero.getSpeed()));
  }

  @Override
  public void down() {
    //а тут плюс
    physics.setVelocityY(hero.getSpeed());
    //physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(hero.getSpeed()));
  }

  @Override
  public void stop() {
    physics.setVelocityY(0);
    physics.setVelocityX(0);
  }

  public void attack() {
    if (!shootTimer.elapsed(
        Duration.millis(hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(
            TimingsModule.class).getIterationTime()))) {
      return;
    }
    int attack = hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(DamageModule.class).getAttack();
    int damage = hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(DamageModule.class).getDamage();
    DamageType damageType = hero.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(DamageModule.class).getDamageType();
    List<Entity> list = getEntityInAttackArea();
    list.forEach(entity -> {
      int k = entity.getComponent(PlayerComponent.class).hero.getArmorCoefficient() - attack;
      if (k>0){
        entity.getComponent(PlayerComponent.class).hero.getModule(HPModule.class).changeHP(damage);
        System.out.println(entity.getComponent(PlayerComponent.class).hero.getModule(HPModule.class).getCurrentHP()+" / "+ entity.getComponent(PlayerComponent.class).hero.getModule(HPModule.class).getMaxHP());
      }
      /*
      FXGL.spawn("projectile", new SpawnData(getEntity().getPosition().getX() + localAnchor.getX(),
          getEntity().getPosition().getY() + localAnchor.getY())
          .put("typeProj", Projectiles.ARROW));
      */
    });
    shootTimer.capture();
  }

  private List<Entity> getEntityInAttackArea() {
    Weapon weapon = hero.getModule(CreatureWeaponModule.class).getActiveWeapon();
    double radius = weapon.getModule(SquareDamageModule.class).getRadius();
    double distance = weapon.getModule(SquareDamageModule.class).getDistance();
    Point2D vectorView = getDirectionView();
    Circle circle = new Circle(getEntity().getX(), getEntity().getY(), distance);
    Point2D r = FXGLMath.rotate(vectorView, new Point2D(0, 0), radius / 2);
    Point2D l = FXGLMath.rotate(vectorView, new Point2D(0, 0), radius / 2 + radius);
    Polygon triangle = new Polygon(
        getEntity().getX(), getEntity().getX(),
        getEntity().getX() + r.getX(), getEntity().getY() + r.getY(),
        getEntity().getX() + l.getX(), getEntity().getY() + l.getY());
    //Line rLine = new Line(getEntity().getX(),getEntity().getY(),
    //    getEntity().getX()+r.getEndX(),getEntity().getY()+r.getEndY());
    List<Entity> list = new ArrayList<>(
        getGameWorld().getEntitiesByType(EntityType.ENEMY).stream()
            .filter(entity -> entity.hasComponent(CollidableComponent.class)
                && circle.contains(entity.getPosition())
                && triangle.contains(entity.getPosition()) && entity!=getEntity()).toList());
    return list;
  }

  public Point2D getDirectionView() {
    return getInput().getVectorToMouse(getEntity().getPosition().add(new Point2D(80, 160)));
  }

  public Creature getHero() {
    return hero;
  }

  enum SIDE {
    LEFT,
    RIGHT
  }
}
