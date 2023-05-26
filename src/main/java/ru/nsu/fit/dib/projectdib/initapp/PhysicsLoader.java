package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner.doAction;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import java.util.List;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.entity.components.DataAttackComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.HPAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.utils.SoundsController;

/**
 * Загразчик физики.
 */
public class PhysicsLoader {

  public PhysicsLoader() {

  }

  public void run() {
    getPhysicsWorld().setGravity(0, 0);
    addAttackCollisionsWithEntityWithHP(List.of(EntityType.PROJECTILE, EntityType.WEAPON),
        List.of(EntityType.PLAYER, EntityType.ENEMY, EntityType.BOX));
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

  private void addAttackCollisionsWithEntityWithHP(List<EntityType> attack,
      List<EntityType> creature) {
    // TODO: 08.05.2023 add box HealthIntComponent 
    creature.forEach(creatureType -> {
      attack.forEach(attackEntityType -> {
        getPhysicsWorld().addCollisionHandler(
            new CollisionHandler(attackEntityType, creatureType) {
              @Override
              protected void onCollisionBegin(Entity projectile, Entity creature) {
                if (projectile.getComponent(DataComponent.class).getOwnerID()
                    == creature.getComponent(
                    DataComponent.class).getId() || !creature.hasComponent(
                    HealthIntComponent.class)) {
                  return;
                }
                var attack = projectile.getComponent(DataAttackComponent.class).getAttack();
                var damage = projectile.getComponent(DataAttackComponent.class).getDamage();
                var defence = 0;
                if (creature.hasComponent(CreatureComponent.class)) {
                  defence = creature.getComponent(CreatureComponent.class).getCreature()
                      .getArmorCoefficient();
                }
                if (attackEntityType == EntityType.PROJECTILE) {
                  projectile.removeFromWorld();
                }
                if (attackEntityType == EntityType.WEAPON) {
                  if (projectile.getComponent(WeaponComponent.class).getWeapon().getType().getWeaponType()== WeaponType.melee && projectile.getComponent(DataComponent.class).getOwnerID()!=-1) {
                    SoundsController.getSoundsController().play(Sounds.attack);
                  }
                  if (projectile.getComponent(WeaponComponent.class).getWeapon().isLongRange()) {
                    return;
                  }
                }
                System.out.println("boba");
                if (!(MCClient.getClientId() == 1 && projectile.getComponent(DataComponent.class).getOwnerID() > 1000) && projectile.getComponent(DataComponent.class).getOwnerID()!= MCClient.getClientId()) {
                  System.out.println(projectile.getComponent(DataComponent.class).getOwnerID());
                  return;
                }
                //System.out.println("attack with ["+"attack:"+attack+", damage:"+damage+"]");
                System.out.println("attac");
                if (attack > defence) {
                  HPAction action = new HPAction(projectile.getComponent(DataComponent.class).getId(),
                      creature.getComponent(DataComponent.class).getId(),damage-(int)(defence*0.30));
                  doAction(new Pair<>(MessageType.HP,action));
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
