package ru.nsu.fit.dib.projectdib.entity.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import java.util.HashMap;
import java.util.Map;

public abstract class CreatureViewComponent extends Component {

  private PhysicsComponent physics;
  private final AnimatedTexture texture;
  Map<State, AnimationChannel> channelMap;

  public CreatureViewComponent(AnimationChannel defaultAnimation) {
    channelMap = new HashMap<>();
    channelMap.put(State.DEFAULT,defaultAnimation);
    texture = new AnimatedTexture(defaultAnimation);
    texture.loop();
    //texture.setPreserveRatio(true);
  }
  @Override
  public void onAdded() {
    entity.getViewComponent().addChild(texture);
    physics = getEntity().getComponent(PhysicsComponent.class);
  }
  protected void addAnimationChannel(State state, AnimationChannel animationChannel) {
    channelMap.put(state, animationChannel);
  }
  @Override
  public void onUpdate(double tpf) {
    if (physics.isMoving()) {
      if (texture.getAnimationChannel() != channelMap.get(State.MOVING)) {
        texture.loopAnimationChannel(channelMap.get(State.MOVING));
      } else if (texture.getAnimationChannel() != channelMap.get(State.DEFAULT)) {
        texture.loopAnimationChannel(channelMap.get(State.DEFAULT));
      }
    }
  }

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
}