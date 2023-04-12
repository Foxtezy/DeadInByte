package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import com.almasb.fxgl.entity.Entity;
import java.util.Map;
import java.util.Objects;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.entity.components.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;

public class EntityState {
  private final Integer id;
  private Point2D position;
  private Point2D rotation;
  private final Integer bindedEntity; //для player - активное оружие

  public EntityState(Integer id, Point2D position, Point2D rotation, Integer bindedEntity) {
    this.id = id;
    this.position = position;
    this.rotation = rotation;
    this.bindedEntity = bindedEntity;
  }

  public Integer getId() {
    return id;
  }

  public Point2D getPosition() {
    return position;
  }

  public void setPosition(Point2D newCoordinate) {
    this.position = newCoordinate;
  }

  public Point2D getRotation() {
    return rotation;
  }

  public void setRotation(Point2D newAngle) {
    this.rotation = newAngle;
  }

  public void update() {
    if (Objects.equals(id, MCClient.getClientId())) return;

    Map<Integer, Entity> hashTable = MCClient.getClientState().getIdHashTable();
    Entity entity = hashTable.get(id);
    if (entity==null) return;
    // TODO: 12.04.2023 тут свич надо дописать
    switch (entity.getComponent(DataComponent.class).getEntityType()) {
      case PLAYER -> {
        if (position != null) {
          Point2D movingVector = position.add(entity.getPosition().multiply(-1));
          entity.getComponent(HeroComponent.class).moveByVector(movingVector);
        }
        entity.getComponent(HeroComponent.class).bindDirectionView(e -> rotation);

        CreatureWeaponModule module = entity.getComponent(HeroComponent.class).getCreature()
            .getModule(CreatureWeaponModule.class);
        if (bindedEntity!=null){
          Entity weapon = MCClient.getClientState().getIdHashTable().get(bindedEntity);

          if (!weapon.getComponent(WeaponComponent.class).isActive()) {
            module.getNextWeapon();
          }
        }

      }
    }
  }
}