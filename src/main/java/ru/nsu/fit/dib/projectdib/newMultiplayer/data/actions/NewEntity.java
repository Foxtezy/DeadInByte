package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import com.almasb.fxgl.entity.Entity;
import java.util.Objects;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;

public class NewEntity {

  private static final int DEFAULT_SEED = 1;

  private EntityState state;

  public String getEntityType() {
    return entityType;
  }

  private final String entityType;
  private final int seed;

  public NewEntity(String entityType, Integer seed, @NotNull Point2D position,Integer weaponId) {
    this.entityType = entityType;
    this.seed = Objects.requireNonNullElse(seed, DEFAULT_SEED);
    state = new EntityState(MCClient.getClientId(),position,new Point2D(0,0),weaponId);
  }
  public NewEntity(String entityType, Integer seed, EntityState state) {
    this.entityType = entityType;
    this.seed = Objects.requireNonNullElse(seed, DEFAULT_SEED);
    this.state = state;
  }

  public Entity spawn() {
    HeroType hero=HeroType.getByName(entityType);
    if (hero!=null) {
      Entity heroEntity = Factory.spawnHero(hero, state.getPosition(), Objects.equals(state.getId(), MCClient.getClientId()), state.getId(), seed);
      MCClient.getClientState().getIdHashTable().put(state.getId(), heroEntity);
      Entity weaponEntity = Factory.spawnStandardWeapon(state.getActiveWeapon(), state.getId(),heroEntity);
      MCClient.getClientState().getIdHashTable().put(state.getActiveWeapon(), weaponEntity);
      return heroEntity;
    }
    Weapons weapon = Weapons.getByName(entityType);
    if (weapon!=null) {
      Entity weaponEntity = Factory.spawnWeapon(weapon, state.getPosition(), state.getId());
      MCClient.getClientState().getIdHashTable().put(state.getId(), weaponEntity);
      return weaponEntity;
    }
    throw new IllegalArgumentException("Entity type not found");
  }
  public void setWeaponId(Integer weaponId) {
    state.setActiveWeapon(weaponId);
  }

  public Integer getID() {
    return state.getId();
  }

  public Integer getWeaponId() {
    return state.getActiveWeapon();
  }

  public int getSeed() {
    return seed;
  }

  public EntityState getState() {
    return state;
  }
}
