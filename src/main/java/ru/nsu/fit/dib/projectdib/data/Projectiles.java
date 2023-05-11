package ru.nsu.fit.dib.projectdib.data;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._projectile_img;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimationChannel;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;

/**
 * Типы снарядов.
 */
public enum Projectiles {
  ARROW("arrow", 400,new AnimationChannel(_projectile_img,4,320,160, Duration.millis(200),
      0,0),new HitBox(new Point2D(70, 65), BoundingShape.box(140, 30))),
  BULLET("bullet", 600,new AnimationChannel(_projectile_img,4,160,160, Duration.millis(200),
      4,4),new HitBox(new Point2D(70, 65), BoundingShape.box(140, 30))),
  FIREBALL("fireball",250,new AnimationChannel(_projectile_img,4,160,160, Duration.millis(600),
      8,10),new HitBox(new Point2D(70, 65), BoundingShape.box(140, 30)));

  private static final Map<String, Projectiles> map = Arrays.stream(values())
      .collect(
          Collectors.toMap(projectile -> projectile.name, projectile -> projectile));

  private final String name;
  private final Integer speed;
  AnimationChannel moving;
  private final HitBox hitBox;

  Projectiles(String name, Integer speed, AnimationChannel moving, HitBox hitBox) {
    this.name = name;
    this.speed = speed;
    this.moving=moving;
    this.hitBox = hitBox;
  }

  public static Projectiles getByName(String str) {
    return map.get(str);
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
  public HitBox getHitbox() {
    return hitBox;
  }
}
