package ru.nsu.fit.dib.projectdib.entity.components.view;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.image.ImageView;

public abstract class CreatureViewComponent extends Component {
  private final double scale = 0.07;
  private PhysicsComponent physics;
  private final AnimatedTexture texture;
  Map<State, AnimationChannel> channelMap;
  private Function<Entity, Point2D> directionView;

  public CreatureViewComponent(AnimationChannel defaultAnimation) {
    channelMap = new HashMap<>();
    channelMap.put(State.DEFAULT, defaultAnimation);
    texture = new AnimatedTexture(defaultAnimation);
    //texture.loop();
    //texture.setPreserveRatio(true);
  }

  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(new Point2D(15, 20));
    entity.setScaleUniform(scale);
    texture.loop();
    //texture.setCache(true);
    //texture.setCacheHint(CacheHint.SPEED);
    entity.getViewComponent().addChild(texture);
    physics = getEntity().getComponent(PhysicsComponent.class);
    //getEntity().getViewComponent();
  }

  protected void addAnimationChannel(State state, AnimationChannel animationChannel) {
    channelMap.put(state, animationChannel);
  }

  @Override
  public void onUpdate(double tpf) {
    if (physics.isMoving()) {
      if (texture.getAnimationChannel() != channelMap.get(State.MOVING)) {
        texture.loopAnimationChannel(channelMap.get(State.MOVING));
      }
    } else if (texture.getAnimationChannel() != channelMap.get(State.DEFAULT)) {
      texture.loopAnimationChannel(channelMap.get(State.DEFAULT));
    }
    //Поворот
    Point2D mouseVelocity = getDirectionView();

    if (mouseVelocity.angle(1, 0) <= 90) {
      rotate(Side.RIGHT);
    } else {
      rotate(Side.LEFT);
    }
  }

  public AnimatedTexture getView() {
    return texture;
  }

  ;


  enum State {
    DEFAULT,
    WAITING, BACK, MOVING
  }

  public void rotate(Side side) {
    if (side == Side.RIGHT) {
      texture.setScaleX(1);
    } else {
      texture.setScaleX(-1);
    }
  }

  public enum Side {
    LEFT,
    RIGHT
  }

  public void bindDirectionView(Function<Entity, Point2D> directionView) {
    this.directionView = directionView;
  }

  public Point2D getDirectionView() {
    return directionView.apply(getEntity());
  }
}