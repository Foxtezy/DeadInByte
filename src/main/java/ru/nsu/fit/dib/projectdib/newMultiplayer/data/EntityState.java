package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import com.almasb.fxgl.entity.Entity;
import java.util.Map;
import java.util.Objects;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.dib.projectdib.entity.components.control.ServerControlComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.WeaponInventoryComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.EnemyViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HeroViewComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class EntityState {

  private int id;
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
          entity.getComponent(ServerControlComponent.class).moveToPoint(position);
        }
        if (entity.hasComponent(HeroViewComponent.class)){
          entity.getComponent(HeroViewComponent.class).bindDirectionView(e -> rotation);
        }
        else if (entity.hasComponent(EnemyViewComponent.class)){
          entity.getComponent(EnemyViewComponent.class).bindDirectionView(e -> rotation);
        }
        Entity weapon = MCClient.getClientState().getIdHashTable().get(bindedEntity);
        while (entity.getComponent(WeaponInventoryComponent.class).getActiveWeapon()!=weapon) {
          entity.getComponent(WeaponInventoryComponent.class).swapWeapon();
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

  public void setID(int id) {
    this.id = id;
  }
}