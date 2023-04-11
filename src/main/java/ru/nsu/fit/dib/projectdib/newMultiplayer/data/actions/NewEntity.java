package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import static ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction.clientID;

import com.almasb.fxgl.entity.Entity;
import java.util.Objects;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;

public class NewEntity {

  private static final int DEFAULT_SEED = 1;

  public void setId(Integer id) {
    this.id = id;
  }

  private Integer id;
  private EntityState state;

  public String getEntityType() {
    return entityType;
  }

  private final String entityType;
  private final int seed;
  private Integer weaponId;

  public NewEntity(String entityType, Integer seed, Point2D position,Integer weaponId) {
    this.entityType = entityType;
    this.seed = Objects.requireNonNullElse(seed, DEFAULT_SEED);
    state = new EntityState(id,position,new Point2D(0,0),weaponId);
  }
  public NewEntity(String entityType, Integer seed, EntityState state) {
    this.entityType = entityType;
    this.seed = Objects.requireNonNullElse(seed, DEFAULT_SEED);
    this.state = state;
  }

  public Entity spawn() {
    HeroType hero=HeroType.getByName(entityType);
    if (hero!=null) {
      Entity heroEntity = Factory.spawnHero(hero, state.getPosition(), Objects.equals(id, clientID), id, seed);
      MCClient.getClientState().getIdHashTable().put(id, heroEntity);
      Entity weaponEntity = Factory.spawnStandardWeapon(weaponId, heroEntity);
      MCClient.getClientState().getIdHashTable().put(weaponId, weaponEntity);
      return heroEntity;
    }
    Weapons weapon = Weapons.getByName(entityType);
    if (weapon!=null) {
      Entity weaponEntity = Factory.spawnWeapon(weapon, state.getPosition(), id);
      MCClient.getClientState().getIdHashTable().put(id, weaponEntity);
      return weaponEntity;
    }
    throw new IllegalArgumentException("Entity type not found");
  }
  public void setWeaponId(Integer weaponId) {
    this.weaponId = weaponId;
  }

  public Integer getID() {
    return id;
  }

  public Integer getWeaponId() {
    return weaponId;
  }

  public int getSeed() {
    return seed;
  }

  public EntityState getState() {
    return state;
  }
}