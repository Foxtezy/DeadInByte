package ru.nsu.fit.dib.projectdib.moving.components;

import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.Config;

public class ExplosionComponent extends Component {
  private AnimatedTexture texture;
  private final double scale = 1;
  private final AnimationChannel animAllLine;

  public ExplosionComponent() {
    Image image = image("explosion.png");
    int frameWidth = (int) image.getWidth() / 5;
    int frameHeight = (int) image.getHeight() / 4;
    animAllLine =
        new AnimationChannel(image, 5, frameWidth, frameHeight, Duration.seconds(1.5), 0, 19);

    texture = new AnimatedTexture(animAllLine);
    texture.loop();
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
  }
}
