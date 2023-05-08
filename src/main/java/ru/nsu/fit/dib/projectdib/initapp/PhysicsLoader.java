package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import java.util.List;
import ru.nsu.fit.dib.projectdib.EntityType;

/**
 * Загразчик физики.
 */
public class PhysicsLoader {

  public PhysicsLoader() {

  }
  public void run() {
    getPhysicsWorld().setGravity(0, 0);
    addProjectileCollisionsWithEntityWithHP(List.of(EntityType.PLAYER,EntityType.ENEMY,EntityType.BOX));
    addProjectileCollisionsWithStaticObjects(List.of(EntityType.WALL));
    
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

  private void addProjectileCollisionsWithEntityWithHP(List<EntityType> list) {
    // TODO: 08.05.2023 add box HealthIntComponent 
    list.forEach(type ->{
      getPhysicsWorld().addCollisionHandler(
          new CollisionHandler(EntityType.PROJECTILE, type) {
            @Override
            protected void onCollisionBegin(Entity projectile,Entity creature) {
              var hp = creature.getComponent(HealthIntComponent.class);
              if (hp.getValue() > 1) {
                projectile.removeFromWorld();
                hp.damage(1);
                return;
              }
              projectile.removeFromWorld();
              creature.removeFromWorld();
              projectile.removeFromWorld();
            }
          });
    });
  }

  private void addProjectileCollisionsWithStaticObjects(List<EntityType> list) {
    list.forEach(type ->{
      getPhysicsWorld().addCollisionHandler(
          new CollisionHandler(EntityType.PROJECTILE, type) {
            @Override
            protected void onCollisionBegin(Entity arrow, Entity wall) {
              arrow.removeFromWorld();
            }
          });
    });
  }
}
