package ru.nsu.fit.dib.projectdib.entity.moving.components;


import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGL.newLocalTimer;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import ru.nsu.fit.dib.projectdib.data.Config;

import ru.nsu.fit.dib.projectdib.EntityType;

import ru.nsu.fit.dib.projectdib.data.HeroSpecs;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.moving.MovingInterface;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static java.lang.Math.abs;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_height;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_numberColumns;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_width;

/**
 * Описывает движение игрока.
 */
public class PlayerMovingComponent extends Component implements MovingInterface {

  /*
  private final AnimationChannel animWalkRight;
  private final AnimationChannel animWalkLeft;
  private final AnimationChannel animWalkUp;
  private final AnimationChannel animWalkDown;
   */
  private final double scale = 0.07;
  AnimationChannel animationMovement;
  AnimationChannel animationStanding;
  AnimationChannel animationWaiting;
  AnimationChannel animationBack;
  boolean stateCanged = true;
  private PhysicsComponent physics;
  //public String currentWeapon;
  private AnimatedTexture texture;
  private LocalTimer shootTimer = newLocalTimer();
  //  private final double speed = 250;
  private double speed;
  private HeroSpecs specification;
  private String currentWeapon;
  private boolean isMoving = false;

  public PlayerMovingComponent(HeroSpecs specs) {
    this.specification = specs;
    this.speed = specification.getSpeed();
    this.currentWeapon = specification.getMainWeapon();
    //resources.assets.textures
    Image image = image(specification.getPlayerImage());
    //animation settings
    Image img = new Image(_player);
    animationMovement = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(600), 5, 8);
    animationStanding = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(300), 0, 1);
    animationWaiting = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(450), 2, 4);
    animationBack = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(600), 9, 12);
    texture = new AnimatedTexture(animationStanding);
    texture.loop();

    /*
    animWalkDown = new AnimationChannel(image, 4, frameWidth, frameHeight, Duration.seconds(0.66),
        0, 3);
    animWalkUp = new AnimationChannel(image, 4, frameWidth, frameHeight, Duration.seconds(0.66),
        4, 6);
    animWalkLeft = new AnimationChannel(image, 4, frameWidth, frameHeight, Duration.seconds(0.66),
        8, 11);
    animWalkRight = new AnimationChannel(image, 4, frameWidth, frameHeight, Duration.seconds(0.66),
        12, 15);

    texture = new AnimatedTexture(animWalkUp);
    texture.loop();
    texture.stop();
    */
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
    if (isMoving && !isMoving() || !isMoving && isMoving()){
      stateCanged=true;
      isMoving=isMoving();
    }
    Point2D mouseVelocity = getInput().getVectorToMouse(
        getGameWorld().getSingleton(EntityType.PLAYER).getPosition());
    //Поворот
    if (mouseVelocity.angle(1, 0) <= 90) {
      texture.setScaleX(1);
    } else {
      texture.setScaleX(-1);
    }
    //Изменилось ли состояние
    if (stateCanged) {
      //texture.loopAnimationChannel(isMoving() ? animationMovement : animationStanding);
      AnimationChannel animation = texture.getAnimationChannel();
      //Движется или нет
      if (isMoving()) {
        texture.loopAnimationChannel(animationMovement);
      } else {
        texture.loopAnimationChannel(animationStanding);
      }
      stateCanged = false;
      /*
    if (angleDown <= 45) {
      if (animation != animWalkDown || !isMoving) {
        texture.loopAnimationChannel(animWalkDown);
        isMoving = true;
      }
    } else if (angleUp <= 45) {
      if (animation != animWalkUp || !isMoving) {
        texture.loopAnimationChannel(animWalkUp);
        isMoving = true;
      }
    } else if (angleRight < 45) {
      if (animation != animWalkRight || !isMoving) {
        texture.loopAnimationChannel(animWalkRight);
        isMoving = true;
      }
    } else {
      if (animation != animWalkLeft || !isMoving) {
        texture.loopAnimationChannel(animWalkLeft);
        isMoving = true;
      }
    }
*/
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }

  private boolean isMoving() {
    return FXGLMath.abs(physics.getVelocityX()) > 0 || FXGLMath.abs(physics.getVelocityY()) > 0;
  }

  @Override
  public void left() {
    physics.setVelocityX(-speed);
    physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(speed));
  }

  @Override
  public void right() {
    physics.setVelocityX(speed);
    physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(speed));
  }

  @Override
  public void up() {
    //да, тут должен быть именно минус
    physics.setVelocityY(-1 * speed);
    physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(speed));
  }

  @Override
  public void down() {
    //а тут плюс
    physics.setVelocityY(speed);
    physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(speed));
  }

  @Override
  public void stop() {
    physics.setVelocityY(0);
    physics.setVelocityX(0);
  }

  public void shoot() {

    switch (specification.getMainWeapon()) {
      case ("bow"):
        if (!shootTimer.elapsed(Config.SHOOT_DELAY_ARROW)) {
          return;
        }
        FXGL.spawn("projectile", new SpawnData(getEntity().getPosition().getX() + 20,
            getEntity().getPosition().getY() + 30)
            .put("typeProj", Projectiles.ARROW));
        shootTimer.capture();
        break;
      case ("ak"):
        if (!shootTimer.elapsed(Config.SHOOT_DELAY_AK)) {
          return;
        }
        ;
        FXGL.spawn("projectile", new SpawnData(getEntity().getPosition().getX() + 20,
            getEntity().getPosition().getY() + 30)
            .put("typeProj", Projectiles.BULLET));
        shootTimer.capture();
        break;
      /*default:
        currentWeapon = "bow";
        break;
      */
    }


  }

  public void swapWeapons() {
    String change = specification.getMainWeapon();
    specification.setMainWeapon(specification.getSecondWeapon());
    specification.setSecondWeapon(change);
  }

  public HeroSpecs getSpecification() {
    return specification;
  }
}
