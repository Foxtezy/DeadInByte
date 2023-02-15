package ru.nsu.fit.dib.projectdib.init_app;

import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import ru.nsu.fit.dib.projectdib.EntityType;

/**
 * Загразчик физики.
 */
public class PhysicsLoader {

  public PhysicsLoader() {

  }
  public void run() {

    getPhysicsWorld().setGravity(0, 0);
    getPhysicsWorld().addCollisionHandler(
        new CollisionHandler(EntityType.BOX, EntityType.PROJECTILE) {
          @Override
          protected void onCollisionBegin(Entity box, Entity arrow) {
            spawn("coin", box.getCenter());
            box.removeFromWorld();
            arrow.removeFromWorld();
          }
        });

    getPhysicsWorld().addCollisionHandler(
        new CollisionHandler(EntityType.ENEMY, EntityType.PROJECTILE) {
          @Override
          protected void onCollisionBegin(Entity enemy, Entity projectile) {
            var hp = enemy.getComponent(HealthIntComponent.class);
            if (hp.getValue() > 1) {
              projectile.removeFromWorld();
              hp.damage(1);
              return;
            }
            projectile.removeFromWorld();
            enemy.removeFromWorld();
            projectile.removeFromWorld();
          }
        });

    getPhysicsWorld().addCollisionHandler(
        new CollisionHandler(EntityType.CHEST, EntityType.PROJECTILE) {
          @Override
          protected void onCollisionBegin(Entity chest, Entity projectile) {
            var hp = chest.getComponent(HealthIntComponent.class);
            if (hp.getValue() > 1) {
              projectile.removeFromWorld();
              hp.damage(1);
              return;
            }
            projectile.removeFromWorld();
            spawn("coin", chest.getCenter());
            chest.removeFromWorld();
            projectile.removeFromWorld();
          }

        });
    getPhysicsWorld().addCollisionHandler(
        new CollisionHandler(EntityType.PROJECTILE, EntityType.WALL) {
          @Override
          protected void onCollisionBegin(Entity arrow, Entity wall) {
            arrow.removeFromWorld();
          }
        });

    getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
      @Override
      protected void onCollisionBegin(Entity player, Entity coin) {
        coin.removeFromWorld();
      }
    });
    getPhysicsWorld().addCollisionHandler(
        new CollisionHandler(EntityType.PLAYER, EntityType.DOOR_TRIGGER) {
          protected void onCollisionBegin(Entity player, Entity doorTrigger) {
            Entity openedDoor = doorTrigger.getObject("openedDoor");
            Entity closedDoor = spawn("closedDoor", openedDoor.getPosition());
            openedDoor.removeFromWorld();
          }
        });
  }

}
