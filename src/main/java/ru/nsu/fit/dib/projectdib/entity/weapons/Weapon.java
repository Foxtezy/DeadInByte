package ru.nsu.fit.dib.projectdib.entity.weapons;

import javafx.util.Pair;
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
  public Pair<Integer,Integer> getDamageBounds(){
    return new Pair<>(data.minDamage,data.maxDamage);
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
