package ru.nsu.fit.dib.projectdib.entity.components;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._enemy;

import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class EnemyViewComponent extends CreatureViewComponent {
  private static Image image = new Image(_enemy);

  public EnemyViewComponent(int enemyNumber) {
    super(new AnimationChannel(image,8,160,320, Duration.millis(300),
        enemyNumber*8+2,enemyNumber*8+3));
    addAnimationChannel(State.MOVING,new AnimationChannel(image,8,160,320, Duration.millis(8*150),
        enemyNumber*8,enemyNumber*8+7));
  }
}
