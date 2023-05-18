package ru.nsu.fit.dib.projectdib.entity.components.multiplayer;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.WeaponInventoryComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.CreatureViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.EnemyViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HeroViewComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponViewComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class DataComponent extends Component {

  public EntityType getEntityType() {
    return entityType;
  }

  private EntityType entityType;
  private Integer id;
  private int ownerID;

  public DataComponent(EntityType entityType, Integer ownerID, Integer id) {
    if (ownerID != null) {
      this.ownerID = ownerID;
    }
    this.entityType = entityType;
    this.id = id;
  }

  public Point2D getRotation() {
    Point2D rotation = null;
    switch (entityType) {
      case WEAPON -> {
        if (getEntity().hasComponent(WeaponViewComponent.class)) {
          rotation = getEntity().getComponent(WeaponViewComponent.class).getRotation();
        }
      }
      case PLAYER, ENEMY -> {
        if (getEntity().hasComponent(HeroViewComponent.class)) {
          rotation = getEntity().getComponent(HeroViewComponent.class).getDirectionView();
        }
        else if (getEntity().hasComponent(EnemyViewComponent.class)) {
          rotation = getEntity().getComponent(EnemyViewComponent.class).getDirectionView();
        }
      }
      default -> throw new RuntimeException("Unexpected entity with type:" + entityType);
    }
    return rotation;
  }

  public Integer getBindedEntity() {
    if (getEntity().getType()==EntityType.WEAPON) return ownerID;
    if (!getEntity().hasComponent(WeaponInventoryComponent.class)
    || getEntity().getComponent(WeaponInventoryComponent.class).getActiveWeapon()==null
    || getEntity().getComponent(WeaponInventoryComponent.class).getActiveWeapon().getComponent(DataComponent.class)==null) return -1;
    return getEntity().getComponent(WeaponInventoryComponent.class).getActiveWeapon()
        .getComponent(DataComponent.class).getId();
  }

  public Integer getId() {
    return id;
  }

  public Point2D getPosition() {
    return getEntity().getPosition();
  }

  public boolean isClientEntity() {
    return ownerID == MCClient.getClientId();
  }

  public int getOwnerID() {
    return ownerID;
  }

  public boolean isValid() {
    return getPosition() != null && getRotation() != null;
  }

  public void setOwnerID(Integer id) {
    ownerID = id;
  }
}
