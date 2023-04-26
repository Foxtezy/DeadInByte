package ru.nsu.fit.dib.projectdib.entity.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class DataComponent extends Component {

  private Point2D position;
  private Point2D rotation;

  public EntityType getEntityType() {
    return entityType;
  }

  private EntityType entityType;
  private Integer bindedEntity;
  private Integer id;
  private int ownerID=-1;

  public DataComponent(EntityType entityType, Integer ownerID, Integer id) {
    if (ownerID!=null) this.ownerID = ownerID;
    this.entityType = entityType;
    rotation = new Point2D(0, 0);
    this.id = id;
  }
  @Override
  public void onUpdate(double tpf) {
    switch (entityType) {
      case WEAPON -> {
        rotation = getEntity().getComponent(WeaponComponent.class).getRotation();
      }
      case PLAYER -> {
        rotation = getEntity().getComponent(HeroComponent.class).getDirectionView();
        position = getEntity().getPosition();
        WeaponComponent weaponComponent = getEntity().getComponent(HeroComponent.class).getCreature()
            .getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(
                TextureModule.class).getComponent();
        if (weaponComponent != null) {
          bindedEntity = weaponComponent.getEntity().getComponent(DataComponent.class)
              .getId();
        }

      }
      default -> {
        throw new RuntimeException("Unexpected entity with type:" + entityType);
      }
    }
  }
  public Point2D getRotation() {
    return rotation;
  }

  public Integer getBindedEntity() {
    return bindedEntity;
  }

  public Integer getId() {
    return id;
  }

  public Point2D getPosition() {
    return position;
  }

  public boolean isClientEntity() {
    return ownerID == MCClient.getClientId();
  }
  public int getOwnerID() {
    return ownerID;
  }

  public boolean isValid() {
    return position!=null && rotation != null;
  }
}
