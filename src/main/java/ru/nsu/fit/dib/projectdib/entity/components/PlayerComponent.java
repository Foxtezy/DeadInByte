package ru.nsu.fit.dib.projectdib.entity.components;


import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGL.newLocalTimer;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;

import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import ru.nsu.fit.dib.projectdib.data.Config;

import ru.nsu.fit.dib.projectdib.EntityType;

import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.JFXModule;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_height;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_numberColumns;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_width;

/**
 * Описывает движение игрока.
 */
public class PlayerComponent extends CreatureComponent implements MovingInterface {
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

  public PlayerComponent(Creature hero,Point2D localAnchor) {
    this.localAnchor=localAnchor;
    this.hero=hero;
    int heroNumber=hero.getModule(JFXModule.class).getImageID();
    //animation settings
    Image img = new Image(_player);
    animationMovement = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(600), 5+heroNumber*13, 8+heroNumber*13);
    animationStanding = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(300), heroNumber*13, 1+heroNumber*13);
    animationWaiting = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(450), 2+heroNumber*13, 4+heroNumber*13);
    animationBack = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(600), 9+heroNumber*13, 12+heroNumber*13);
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
    if (isMoving && !isMoving() || !isMoving && isMoving()){
      stateChanged =true;
      isMoving=isMoving();
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

    Point2D mouseVelocity = getInput().getVectorToMouse(
        getGameWorld().getSingleton(EntityType.PLAYER).getPosition());
    //Поворот
    if (mouseVelocity.angle(1, 0) <= 90) {
      rotate(SIDE.RIGHT);
    } else {
      rotate(SIDE.LEFT);
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }
  enum SIDE{
    LEFT,
    RIGHT
  }
  public void rotate(SIDE side){
    if (side==SIDE.RIGHT) {
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
    if (!shootTimer.elapsed(Config.SHOOT_DELAY_ARROW)) {
      return;
    }
    FXGL.spawn("projectile", new SpawnData(getEntity().getPosition().getX() + localAnchor.getX(),
        getEntity().getPosition().getY() + localAnchor.getY())
        .put("typeProj", Projectiles.ARROW));
    shootTimer.capture();

  }
  public Creature getHero(){
    return hero;
  }
  /*
  public Point2D getVectorView(){
    return getInput().getVectorToMouse(getEntity().getPosition());
  }*/
}
