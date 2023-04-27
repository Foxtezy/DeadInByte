package ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules;

import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.SpecsModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.WeaponSkillsModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class DamageModule extends WeaponModule {

  protected WeaponType weaponType;
  protected DamageType damageType;
  Creature user;
  private int minDamage;
  private int maxDamage;

  public DamageModule(WeaponType weaponType, DamageType damageType, int minDamage, int maxDamage) {
    this.weaponType = weaponType;
    this.damageType = damageType;
    this.minDamage = minDamage;
    this.maxDamage = maxDamage;
  }

  public void setUser(Creature user) {
    this.user = user;
  }

  public int getAttack() {
    // TODO: 26.04.2023  заглушка 
    if (true) return 0;
    return RandomSystem.d(20) + user.getModule(WeaponSkillsModule.class).getSkills(weaponType) +
        switch (weaponType) {
          case melee -> user.getModule(SpecsModule.class).getStrengthModifier();
          case shooting -> user.getModule(SpecsModule.class).getAgilityModifier();
          case magic -> Math.max(user.getModule(SpecsModule.class).getWisdomModifier(),
              user.getModule(SpecsModule.class).getIntelligenceModifier());
          case fencing, throwing ->
              Math.max(user.getModule(SpecsModule.class).getStrengthModifier(),
                  user.getModule(SpecsModule.class).getAgilityModifier());
        };
  }

  public int getDamage() {
    return RandomSystem.getRandInt(minDamage, maxDamage) + switch (weaponType) {
      case melee, shooting -> user.getModule(SpecsModule.class).getStrengthModifier();
      case fencing, throwing -> Math.max(user.getModule(SpecsModule.class).getStrengthModifier(),
          user.getModule(SpecsModule.class).getAgilityModifier());
      case magic -> Math.max(user.getModule(SpecsModule.class).getWisdomModifier(),
          user.getModule(SpecsModule.class).getIntelligenceModifier());
    };
  }

  protected WeaponType getWeaponType() {
    return weaponType;
  }

  protected void setWeaponType(WeaponType weaponType) {
    this.weaponType = weaponType;
  }

  public DamageType getDamageType() {
    return damageType;
  }

  protected void setDamageType(DamageType damageType) {
    this.damageType = damageType;
  }

  protected int getMinDamage() {
    return minDamage;
  }

  protected void setMinDamage(int minDamage) {
    this.minDamage = minDamage;
  }

  protected int getMaxDamage() {
    return maxDamage;
  }

  protected void setMaxDamage(int maxDamage) {
    this.maxDamage = maxDamage;
  }

}
