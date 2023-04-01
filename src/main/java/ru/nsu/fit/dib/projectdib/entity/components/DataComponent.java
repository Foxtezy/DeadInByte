package ru.nsu.fit.dib.projectdib.entity.components;

import com.almasb.fxgl.entity.component.Component;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.data.json.update.Action;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class DataComponent extends Component {

  private Point2D position;
  private Point2D rotation;
  private List<Action> actions; //для player

  public EntityType getEntityType() {
    return entityType;
  }

  private EntityType entityType;
  private Integer bindedEntity;
  private Integer id;
  private Boolean isClientEntity;

  public DataComponent(EntityType entityType, boolean isClientEntity, Integer id) {
    this.isClientEntity = isClientEntity;
    this.entityType = entityType;
    rotation = new Point2D(0, 0);
    actions = new ArrayList<>();
    this.id = id;
  }

  @Override
  public void onAdded() {
    actions = new ArrayList<>();
  }

  @Override
  public void onUpdate(double tpf) {
    switch (entityType) {
      case WEAPON -> {
        rotation = getEntity().getComponent(WeaponComponent.class).getRotation();
      }
      case PLAYER -> {
        rotation = getEntity().getComponent(HeroComponent.class).getDirectionView();
        WeaponComponent weaponComponent =getEntity().getComponent(HeroComponent.class).getCreature()
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

  public void addAction(Action action) {
    actions.add(action);
  }

  public List<Action> getActions() {
    List<Action> currActions = List.copyOf(actions);
    actions.clear();
    return currActions;
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
    return isClientEntity;
  }
}
