package ru.nsu.fit.dib.projectdib.data;

import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.CreatureBuilder;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class HeroData {
  private final HeroType type;
  private final Weapons weapon;
  private final int maxHP;
  private final int hp;
  private final int[] specs;
  private final int[] skills;

  public HeroData(HeroType type, Weapons weapon,int MaxHP,int HP, int[] specs, int[] skills) {
    this.type = type;
    this.weapon = weapon;
    maxHP = MaxHP;
    hp = HP;
    this.specs = specs;
    this.skills = skills;
  }
  public Creature toCreature(){
    return new CreatureBuilder(type.getName(),type)
        .setHP(maxHP)
        .setCurrentHP(hp)
        .setSpecs(specs)
        .setWeapon(weapon)
        .setSkill(WeaponType.throwing,skills[0])
        .setSkill(WeaponType.shooting,skills[1])
        .setSkill(WeaponType.fencing,skills[2])
        .setSkill(WeaponType.melee,skills[3])
        .setSkill(WeaponType.magic,skills[4])
        .build();
  }
}