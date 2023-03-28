package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.data.json.update.Action;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.WeaponSkillsModule;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class EntityState {
  private final Integer id;
  private Point2D position;
  private Point2D rotation;
  private List<Action> actions; //для player
  private Integer bindedEntity; //для player - активное оружие
  public Integer getId() {
    return id;
  }

  public Point2D getPosition() {
    return position;
  }

  public Point2D getRotation() {
    return rotation;
  }
  
  public void setPosition(Point2D newCoordinate){
    this.position = newCoordinate;
  }
  public void setRotation(Point2D newAngle){
    this.rotation = newAngle;
  }
  public EntityState(Integer id, Point2D position, Point2D rotation, List<Action> actions,Integer bindedEntity){
    this.id=id;
    this.position = position;
    this.rotation = rotation;
    this.actions = actions;
    this.bindedEntity=bindedEntity;
  }
  public void updateCreature(Entity creature){
    if (position!=null){
      Point2D movingVector = position.add(creature.getPosition().multiply(-1));
      creature.getComponent(HeroComponent.class).moveByVector(movingVector);
    }

    creature.getComponent(HeroComponent.class).bindDirectionView(entity -> rotation);
    actions.forEach(action -> {
      switch (action.getType()){
        case ATTACK -> creature.getComponent(HeroComponent.class).attack();
        case TAKE -> creature.getComponent(HeroComponent.class).takeWeapon(
            MCClient.getClientState().getIdHashTable().get(action.getObjectOfAction()));
        case THROW -> creature.getComponent(HeroComponent.class).throwWeapon();
      }
    });
    CreatureWeaponModule module = creature.getComponent(HeroComponent.class).getCreature().getModule(CreatureWeaponModule.class);
    if (!MCClient.getClientState().getIdHashTable().get(bindedEntity).getComponent(WeaponComponent.class).isActive()){
      creature.getComponent(HeroComponent.class).getCreature().getModule(CreatureWeaponModule.class).getNextWeapon();
    }
  }
  public void updateWeapon(Entity weapon) {
    if (position!=null) weapon.setPosition(position);
      if (rotation!=null) weapon.setRotation(rotation.angle(new Point2D(0,0)));
  }

  public boolean isCreature() {
    return bindedEntity != null;
  }
}
