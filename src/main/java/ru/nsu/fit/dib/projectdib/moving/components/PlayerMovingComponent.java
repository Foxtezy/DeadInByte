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

  private final AnimationChannel animWalkRight;
  private final AnimationChannel animWalkLeft;
  private final AnimationChannel animWalkUp;
  private final AnimationChannel animWalkDown;

  private final double speed = 250;

  private final double scale = 0.15;

  private boolean isMoving = false;

  public PlayerMovingComponent() {
    //resources.assets.textures
    Image image = image("player.png");

    //animation settings
    int frameWidth = (int) image.getWidth() / 4;
    int frameHeight = (int) image.getHeight() / 4;
    animWalkDown = new AnimationChannel(image, 4, frameWidth, frameHeight, Duration.seconds(0.66), 0, 3);
    animWalkUp = new AnimationChannel(image, 4, frameWidth, frameHeight, Duration.seconds(0.66), 4, 6);
    animWalkLeft = new AnimationChannel(image, 4, frameWidth, frameHeight, Duration.seconds(0.66), 8, 11);
    animWalkRight = new AnimationChannel(image, 4, frameWidth, frameHeight, Duration.seconds(0.66), 12, 15);

    texture = new AnimatedTexture(animWalkUp);
    texture.loop();
    texture.stop();
  }

  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(new Point2D(15, 20));
    entity.getViewComponent().addChild(texture);
    getEntity().setScaleUniform(scale);
  }

  @Override
  public void onUpdate(double tpf) {
    AnimationChannel animation = texture.getAnimationChannel();
    double angleRight = physics.getLinearVelocity().angle(1, 0);
    double angleUp = physics.getLinearVelocity().angle(0, -1);
    double angleLeft = physics.getLinearVelocity().angle(-1, 0);
    double angleDown = physics.getLinearVelocity().angle(0, 1);
    if (physics.getLinearVelocity().magnitude() < 10) {
      if (isMoving) {
        texture.stop();
        isMoving = false;
      }
    } else if (angleDown <= 45) {
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
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }

  @Override
  public void left() {
    physics.setVelocityX(-1 * speed);
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
}
