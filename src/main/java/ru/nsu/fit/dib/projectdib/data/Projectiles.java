package ru.nsu.fit.dib.projectdib.data;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._projectile_img;

import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

/**
 * Типы снарядов.
 */
public enum Projectiles {
  ARROW("arrow", 400,new AnimationChannel(_projectile_img,4,320,160, Duration.millis(200),
      0,0)),
  BULLET("bullet", 600,new AnimationChannel(_projectile_img,4,160,160, Duration.millis(200),
      4,4)),
  FIREBALL("bullet",250,new AnimationChannel(_projectile_img,4,160,160, Duration.millis(600),
      8,10));


  private final String name;
  private final Integer speed;
  AnimationChannel moving;
  Projectiles(String name, Integer speed, AnimationChannel moving) {
    this.name = name;
    this.speed = speed;
    this.moving=moving;
  }

  public String getName() {
    return name;
  }

  public Integer getSpeed() {
    return speed;
  }

  public AnimationChannel getAnimation() {
    return moving;
  }
}
