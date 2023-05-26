package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static ru.nsu.fit.dib.projectdib.initapp.GameInitializer.chunkLoader;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.util.Platform;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.ui.UIController;
import java.util.Objects;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.dib.projectdib.App;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.Spawner;
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
import ru.nsu.fit.dib.projectdib.ui.GameUIController;

public class NewEntity {

  private static final int DEFAULT_SEED = 1;

  private EntityState state;

  public String getEntityType() {
    return entityType;
  }

  private final String entityType;
  private final int seed;
  private int owner;

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
    switch (TypeChooser.getTypeByString(entityType)){
      case ENEMY ->{

        Creature creature = EnemiesFactory.newEnemy(EnemyType.getByName(entityType),seed);
        return newEntity(owner, creature);
      }
      case PLAYER ->{
        Creature creature = HeroesFactory.newHero(HeroType.getByName(entityType),seed);;
        Entity entity = newEntity(owner, creature);
        synchronized (App.uiController.queue){
          if (App.uiController == null) {
            GameUIController.queue.add(entity);
          } else {
            App.uiController.addHPBar(entity);
          }
        }
        if (MCClient.getClientId()==state.getId()) {
          entity.addComponent(chunkLoader);
          Viewport viewport = FXGL.getGameScene().getViewport();
          viewport.focusOn(entity);
          viewport.bindToEntity(entity, viewport.getWidth() / 2-40, viewport.getHeight() / 2-120);
          GameUIController.newCharacterMenu();
        }
        return entity;
      }
      case WEAPON->{
        Weapon weapon = WeaponFactory.getWeapon(Weapons.getByName(entityType));
        Entity weaponEntity = Factory.spawnWeapon(weapon,state.getPosition(), state.getActiveWeapon(),owner);
        MCClient.getClientState().getIdHashTable().put(state.getActiveWeapon(), weaponEntity);
      }
      case PROJECTILE ->{
        SpawnData sd = new SpawnData(state.getPosition());
        sd.put("owner",state.getActiveWeapon());
        sd.put("direction",state.getRotation());
        sd.put("attack",seed/1000);
        sd.put("damage",seed%1000);
        sd.put("id",state.getId());
        sd.put("projectileType",Projectiles.getByName(entityType));
        Spawner.spawn("projectile",sd);
      }
      default -> throw new IllegalArgumentException("Entity type not found");
    }
    return null;
  }

  @NotNull
  private Entity newEntity(int owner, Creature creature) {
    Weapon weapon = WeaponFactory.getWeapon(creature.getStandardWeapon());
    Entity creatureEntity = Factory.spawnCreature(creature,state.getPosition(),state.getId(),owner);
    Entity weaponEntity = Factory.spawnWeapon(weapon, state.getPosition(),state.getActiveWeapon(),-1);
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
