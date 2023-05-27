package ru.nsu.fit.dib.projectdib.entity.components.projectile;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import ru.nsu.fit.dib.projectdib.EntityType;

public class ProjectileCollisionComponent extends Component {

  @Override
  public void onUpdate(double tpf) {
    boolean collide = FXGL.getGameWorld().getEntitiesByType(EntityType.WALL).stream().anyMatch(c -> c.isColliding(entity));
    if (collide) {
      entity.removeFromWorld();
    }
  }
}
