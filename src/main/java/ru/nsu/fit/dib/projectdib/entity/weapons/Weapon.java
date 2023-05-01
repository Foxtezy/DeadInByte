package ru.nsu.fit.dib.projectdib.entity.weapons;

import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;

public class Weapon {
  WeaponBuilder data;
  public Weapon(WeaponBuilder data) {
    this.data=data;
  }

  public Weapons getType() {
    return data.weapon;
  }

  public double getDistance() {
    return data.distance;
  }
  public double getRadius(){
    return data.radius;
  }
}
