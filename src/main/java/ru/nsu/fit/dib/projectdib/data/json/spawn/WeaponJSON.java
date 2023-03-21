package ru.nsu.fit.dib.projectdib.data.json.spawn;

import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;

public class WeaponJSON {


  Integer id;
  private Weapons weapon;
  private Integer seed;

  public Integer getId() {
    return id;
  }

  public Integer getSeed() {
    return seed;
  }

  public Weapons getWeapon() {
    return weapon;
  }
}
