package ru.nsu.fit.dib.projectdib.entity.weapons;

import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;

public class WeaponBuilder {
  final String name;
  final Weapons weapon;
  int maxDamage;
  int minDamage;
  double distance;
  double radius;
  int rollbackTime;
  int impactSpeed;

  public WeaponBuilder(String name, Weapons weapon) {
    this.name = name;
    this.weapon=weapon;
  }

  public WeaponBuilder setDamage(int minDamage, int maxDamage) {
    this.minDamage=minDamage;
    this.maxDamage=maxDamage;
    return this;
  }
  public WeaponBuilder setScope(double distance, double radius) {
    this.distance = distance;
    this.radius = radius;
    return this;
  }

  public WeaponBuilder setTime(int rollbackTime, int impactSpeed) {
    this.rollbackTime = rollbackTime;
    this.impactSpeed = impactSpeed;
    return this;
  }

  public Weapon build() {
    return new Weapon(this);
  }
}
