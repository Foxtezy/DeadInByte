package ru.nsu.fit.dib.projectdib.entity.components.projectile;

import static ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner.doAction;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import java.util.Optional;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.DataAttackComponent;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.HPAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;

public class ProjectileCollisionComponent extends Component {

  @Override
  public void onUpdate(double tpf) {
    boolean collide = FXGL.getGameWorld().getEntitiesByType(EntityType.WALL).stream().anyMatch(c -> c.isColliding(entity));
    if (collide) {
      entity.removeFromWorld();
    }
    Optional<Entity> creatureOpt = FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY, EntityType.PLAYER).stream().filter(c -> c.isColliding(entity)).findAny();
    if (creatureOpt.isEmpty()) return;
    Entity creature = creatureOpt.get();
    if (entity.getComponent(DataComponent.class).getOwnerID()
        == creature.getComponent(
        DataComponent.class).getId() || !creature.hasComponent(
        HealthIntComponent.class)) {
      return;
    }
    var attack = entity.getComponent(DataAttackComponent.class).getAttack();
    var damage = entity.getComponent(DataAttackComponent.class).getDamage();
    var defence = 0;
    if (creature.hasComponent(CreatureComponent.class)) {
      defence = creature.getComponent(CreatureComponent.class).getCreature()
          .getArmorCoefficient();
    }
    entity.removeFromWorld();
    if (!(MCClient.getClientId() == 1 && entity.getComponent(DataComponent.class).getOwnerID() > 1000) && entity.getComponent(DataComponent.class).getOwnerID()!= MCClient.getClientId()) {
      return;
    }
    if (entity.getComponent(DataComponent.class).getOwnerID() > 1000 && creature.getType() == EntityType.ENEMY) {
      return;
    }
    if (attack > defence) {
      HPAction action = new HPAction(entity.getComponent(DataComponent.class).getId(),
          creature.getComponent(DataComponent.class).getId(),damage-(int)(defence*0.30));
      doAction(new Pair<>(MessageType.HP,action));
    }
  }
}
