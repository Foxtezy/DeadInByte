package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Factory implements EntityFactory {
  @Spawns("player")
  public Entity newPlayer(SpawnData data) {
    return entityBuilder()
        .from(data)
        .type(EntityType.PLAYER)
        .viewWithBBox(new Rectangle(30, 30, Color.BLUE))
        .collidable()
        .build();
  }

  @Spawns("platform")
  public Entity platform(SpawnData data) {
    return entityBuilder(data)
        .from(data)
        .type(EntityType.PLATFORM)
        .bbox(
            new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        .with(new CollidableComponent(true))
        .build();
  }

  @Spawns("wall")
  public Entity wall(SpawnData data) {
    return entityBuilder(data)
        .from(data)
        .type(EntityType.WALL)
        .bbox(
            new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
        .with(new CollidableComponent(true))
        .collidable()
        .build();
  }
}
