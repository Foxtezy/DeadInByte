package ru.nsu.fit.dib.projectdib.entity.weapons;

import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

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
  public int getRollbackTime(){
    return data.rollbackTime;
  }

  public boolean isLongRange() {
    return data.weapon.getWeaponType() == WeaponType.shooting || data.weapon.getWeaponType() == WeaponType.throwing || data.weapon.getWeaponType() == WeaponType.magic;
  }
}
