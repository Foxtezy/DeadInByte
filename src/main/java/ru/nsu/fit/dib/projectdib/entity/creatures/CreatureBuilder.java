package ru.nsu.fit.dib.projectdib.entity.creatures;

import java.util.HashMap;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class CreatureBuilder {

  final String name;
  CreatureType type;
  int level = 1;
  int xp = 0;
  HashMap<WeaponType, Integer> skills = new HashMap<>(Map.of(
      WeaponType.throwing, 0,
      WeaponType.melee, 0,
      WeaponType.fencing, 0,
      WeaponType.shooting, 0,
      WeaponType.magic, 0));
  Weapons weaponType;
  int[] specs = new int[]{0, 0, 0, 0, 0, 0};
  int currentHP = 1;
  int hp = 1;

  public CreatureBuilder(String name, CreatureType type) {
    this.name = name;
    this.level = level;
    this.type = type;
  }

  public CreatureBuilder setLevel(int level) {
    this.level = level;
    return this;
  }

  public CreatureBuilder setXP(int xp) {
    this.xp = xp;
    return this;
  }

  public CreatureBuilder setSkill(WeaponType skill, Integer value) {
    skills.remove(skill);
    skills.put(skill, value);
    return this;
  }

  public CreatureBuilder setHP(int hp) {
    this.hp = hp;
    this.currentHP = hp;
    return this;
  }

  public CreatureBuilder setCurrentHP(int currentHP) {
    this.currentHP = currentHP;
    return this;
  }

  public CreatureBuilder setSpecs(int strength, int agility, int durability,
      int intelligence, int wisdom, int charisma) {
    int[] specs = new int[]{strength, agility, durability, intelligence, wisdom, charisma};
    setSpecs(specs);
    return this;
  }
  public CreatureBuilder setSpecs(int[] specs) {
    this.specs = specs;
    return this;
  }
  public CreatureBuilder setWeapon(Weapons weaponType) {
    this.weaponType = weaponType;
    return this;
  }

  public Creature build() {
    return new Creature(this);
  }
}
