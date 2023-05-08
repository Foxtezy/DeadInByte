package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import java.util.List;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.DataDamageComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.control.PlayerControlComponent;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;

/**
 * Загразчик физики.
 */
public class PhysicsLoader {

  public PhysicsLoader() {

  }

  public void run() {
    getPhysicsWorld().setGravity(0, 0);
    addAttackCollisionsWithEntityWithHP(List.of(EntityType.PROJECTILE,EntityType.WEAPON), List.of(EntityType.PLAYER, EntityType.ENEMY, EntityType.BOX));
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

  private void addAttackCollisionsWithEntityWithHP(List<EntityType> attack, List<EntityType> creature) {
    // TODO: 08.05.2023 add box HealthIntComponent 
    creature.forEach(creatureType -> {
      attack.forEach(attackEntityType ->{
      getPhysicsWorld().addCollisionHandler(
          new CollisionHandler(attackEntityType, creatureType) {
            @Override
            protected void onCollisionBegin(Entity projectile, Entity creature) {
              if (projectile.getComponent(DataComponent.class).getOwnerID()==creature.getComponent(
                  DataComponent.class).getId()) return;
              var attack = projectile.getComponent(DataDamageComponent.class).getAttack();
              var damage = projectile.getComponent(DataDamageComponent.class).getDamage();
              var defence = 0;
              if (creature.hasComponent(CreatureComponent.class)) {
                defence = creature.getComponent(CreatureComponent.class).getCreature()
                    .getArmorCoefficient();
              }
              var hp = creature.getComponent(HealthIntComponent.class);
              if (attackEntityType==EntityType.PROJECTILE) projectile.removeFromWorld();
              if (attackEntityType==EntityType.WEAPON){
                if (projectile.getComponent(WeaponComponent.class).getWeapon().isLongRange()) return;
              }
              if (attack>defence){
                hp.damage(damage);
              }
              if (hp.isZero()) {
                if (creatureType==EntityType.PLAYER && creature.hasComponent(PlayerControlComponent.class)) {
                  //FXGL.getGameController().exit();// TODO: 08.05.2023 изменить
                  return;
                }
                creature.removeFromWorld();
              }

            }
          });
      });

    });
  }

  private void addProjectileCollisionsWithStaticObjects(List<EntityType> list) {
    list.forEach(type -> {
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
