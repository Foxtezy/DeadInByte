package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.newentities;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class NewWeapon extends NewEntity {

  Weapons weapon;

  public NewWeapon(Point2D position, Weapons weapon) {
    super(position);
    this.weapon = weapon;
  }

  @Override
  public Entity spawn() {
    Entity weaponEntity = Factory.spawnWeapon(weapon, position, id);
    MCClient.getClientState().getIdHashTable().put(id, weaponEntity);
    return weaponEntity;
  }
}
