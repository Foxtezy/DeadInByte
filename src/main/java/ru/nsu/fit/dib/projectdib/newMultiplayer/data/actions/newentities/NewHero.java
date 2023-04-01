package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.newentities;

import static ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction.clientID;

import com.almasb.fxgl.entity.Entity;
import java.util.Objects;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class NewHero extends NewEntity {

  private final HeroType hero;
  private final Integer seed;
  private Integer weaponId;

  public NewHero(HeroType hero, Integer seed, Point2D position) {
    super(position);
    this.hero = hero;
    this.seed = seed;
  }

  public Entity spawn() {
    Entity heroEntity = Factory.spawnHero(hero, position, Objects.equals(id, clientID), id, seed);
    MCClient.getClientState().getIdHashTable().put(id, heroEntity);
    Entity weaponEntity = Factory.spawnStandardWeapon(weaponId, heroEntity);
    MCClient.getClientState().getIdHashTable().put(weaponId, weaponEntity);
    return heroEntity;
  }
  public void setWeaponId(Integer weaponId) {
    this.weaponId = weaponId;
  }
}
