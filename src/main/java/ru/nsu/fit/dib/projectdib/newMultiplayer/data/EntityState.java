package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import com.almasb.fxgl.entity.Entity;
import java.util.Map;
import java.util.Objects;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.dib.projectdib.entity.components.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class EntityState {

  private final int id;
  private Point2D position;
  private Point2D rotation;
  private int bindedEntity=-1; //для player - активное оружие

  public EntityState(int id, @NotNull Point2D position, @NotNull Point2D rotation, Integer bindedEntity) {
    this.id = id;
    this.position = position;
    this.rotation = rotation;
    if (bindedEntity!=null) {
      this.bindedEntity = bindedEntity;
    }
  }

  public int getId() {
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
    // TODO: 21.04.2023 иногда выскакивает DataComponent not found
    switch (entity.getComponent(DataComponent.class).getEntityType()) {
      case PLAYER -> {
        if (position != null) {
          // TODO: 21.04.2023 проверка HeroComponent
          entity.getComponent(HeroComponent.class).moveToPoint(position);
        }
        entity.getComponent(HeroComponent.class).bindDirectionView(e -> rotation);

        CreatureWeaponModule module = entity.getComponent(HeroComponent.class).getCreature()
            .getModule(CreatureWeaponModule.class);

        Entity weapon = MCClient.getClientState().getIdHashTable().get(bindedEntity);
        if (weapon!=null && !weapon.getComponent(WeaponComponent.class).isActive()) {
          module.getNextWeapon();
        }


      }
    }
  }

  public int getActiveWeapon() {
    return bindedEntity;
  }

  public void setActiveWeapon(Integer weaponId) {
    this.bindedEntity=weaponId;
  }

  @Override
  public String toString() {
    return "EntityState{" +
        "id=" + id +
        ", position=" + position +
        ", rotation=" + rotation +
        ", bindedEntity=" + bindedEntity +
        '}';
  }
}