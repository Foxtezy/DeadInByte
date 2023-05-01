package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import com.almasb.fxgl.entity.Entity;
import java.util.Objects;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.creatures.TypeChooser;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
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
    int owner;
    if (state.getId()>0 && state.getId()<4){
      owner=state.getId();
    }
    else {
      owner=-1;
    }
    System.out.println(owner+"  "+entityType);
    switch (TypeChooser.getTypeByString(entityType)){
      case ENEMY ->{
        System.out.println(entityType);
        Creature creature = EnemiesFactory.newEnemy(EnemyType.getByName(entityType),seed);
        return newEntity(owner, creature);
      }
      case PLAYER ->{
        Creature creature = HeroesFactory.newHero(HeroType.getByName(entityType),seed);
        return newEntity(owner, creature);
      }
      case WEAPON->{
        Weapon weapon = WeaponFactory.getWeapon(Weapons.getByName(entityType));
        Entity weaponEntity = Factory.spawnWeapon(weapon,state.getPosition(), state.getActiveWeapon(),owner);
        MCClient.getClientState().getIdHashTable().put(state.getActiveWeapon(), weaponEntity);
      }
      default -> throw new IllegalArgumentException("Entity type not found");
    }
    return null;
  }

  @NotNull
  private Entity newEntity(int owner, Creature creature) {
    Weapon weapon = WeaponFactory.getWeapon(creature.getStandardWeapon());
    Entity creatureEntity = Factory.spawnCreature(creature,state.getPosition(),state.getId(),owner);
    Entity weaponEntity = Factory.spawnWeapon(weapon, state.getPosition(),state.getActiveWeapon(),owner);
    MCClient.getClientState().getIdHashTable().put(state.getActiveWeapon(), weaponEntity);
    MCClient.getClientState().getIdHashTable().put(state.getId(), creatureEntity);
    return creatureEntity;
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
