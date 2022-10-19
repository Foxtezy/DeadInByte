package ru.nsu.fit.dib.projectdib.moving.components;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.moving.MovingInterface;

public class PlayerMovingComponent extends Component implements MovingInterface {

  private PhysicsComponent physics;

  private AnimatedTexture texture;

  private AnimationChannel animIdle;

  private final AnimationChannel animWalkLeftRight;
  private final AnimationChannel animWalkUp;
  private final AnimationChannel animWalkBack;

  private final double speed = 250;

  public PlayerMovingComponent() {
    //resources.assets.textures
    Image image = image("some.png");

    //animation settings
    animIdle = new AnimationChannel(image, 4, 32, 42, Duration.seconds(1), 1, 1);
    animWalkLeftRight = new AnimationChannel(image, 4, 32, 42, Duration.seconds(0.66), 0, 3);
    animWalkUp = new AnimationChannel(image, 4, 32, 42, Duration.seconds(0.66), 4, 7);
    animWalkBack = new AnimationChannel(image, 4, 32, 42, Duration.seconds(0.66), 8, 11);

    texture = new AnimatedTexture(animIdle);
    texture.loop();
  }

  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
    entity.getViewComponent().addChild(texture);
  }

  @Override
  public void onUpdate(double tpf) {
    AnimationChannel animation = texture.getAnimationChannel();
    double angle = physics.getLinearVelocity().angle(1, 0);
    if (physics.getLinearVelocity().magnitude() < 10 && animation != animIdle) {
      texture.loopAnimationChannel(animIdle);
      return;
    } else if (angle <= -45 && angle >= -135 && animation != animWalkBack) {
      texture.loopAnimationChannel(animWalkBack);
    } else if (angle >= 45 && angle <= 135 && animation != animWalkUp) {
      texture.loopAnimationChannel(animWalkUp);
    } else if (animation != animWalkLeftRight) {
      texture.loopAnimationChannel(animWalkLeftRight);
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }

  @Override
  public void left() {
    getEntity().setScaleX(-1);
    physics.setVelocityX(-1 * speed);
    physics.setLinearVelocity(physics.getLinearVelocity().normalize().multiply(speed));
  }

  @Override
  public void right() {
    getEntity().setScaleX(1);
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
}
