package ru.nsu.fit.dib.projectdib.entity.moving.components;

import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGL.newLocalTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_height;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_numberColumns;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_width;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.data.HeroSpecs;

public class CreatureComponent extends Component {


  private final double scale = 0.07;
  AnimationChannel animationMovement;
  AnimationChannel animationStanding;
  AnimationChannel animationWaiting;
  AnimationChannel animationBack;
  Point2D localAnchor;
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

  public void MovingComponent(HeroSpecs specs,Point2D localAnchor) {
    int heroNumber=2;
    this.localAnchor=localAnchor;
    this.specification = specs;
    this.speed = specification.getSpeed();
    this.currentWeapon = specification.getMainWeapon();
    //resources.assets.textures
    Image image = image(specification.getPlayerImage());
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
      stateCanged=true;
      isMoving=isMoving();
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
    }

    Point2D mouseVelocity = getInput().getVectorToMouse(
        getGameWorld().getSingleton(EntityType.PLAYER).getPosition());
    //Поворот
    if (mouseVelocity.angle(1, 0) <= 90) {
      rotate(PlayerComponent.SIDE.RIGHT);
    } else {
      rotate(PlayerComponent.SIDE.LEFT);
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }
  enum SIDE{
    LEFT,
    RIGHT
  }
  public void rotate(PlayerComponent.SIDE side){
    if (side== PlayerComponent.SIDE.RIGHT) {
      texture.setScaleX(1);
    } else {
      texture.setScaleX(-1);
    }
  }
  private boolean isMoving() {
    return FXGLMath.abs(physics.getVelocityX()) > 0 || FXGLMath.abs(physics.getVelocityY()) > 0;
  }
}
