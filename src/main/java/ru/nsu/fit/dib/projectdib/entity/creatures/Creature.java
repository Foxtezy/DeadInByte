package ru.nsu.fit.dib.projectdib.entity.creatures;

import java.util.HashMap;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.HPModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.JFXModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.SpecsModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.WeaponSkillsModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.DamageModule;

public final class Creature {

  private final CreatureRarity rarity;  // Редкость персонажа
  private final String name;            // Имя
  private final int level;              // Уровень
  private HashMap<Class<? extends CreatureModule>, ? extends CreatureModule> modules;
  public Creature(CreatureBuilder creatureBuilder) {
    modules = creatureBuilder.modules;
    this.rarity = creatureBuilder.rarity;
    this.name = creatureBuilder.name;
    this.level = creatureBuilder.level;
    if (modules.containsKey(CreatureWeaponModule.class)) {
      getModule(CreatureWeaponModule.class).setWeaponModuleUser(this);
    }
  }

  public <T extends CreatureModule> T getModule(Class<T> type) {
    return (T) modules.get(type);
  }

  /*
  public boolean hit(Creature creature) {
    return creature.getDamage(this,
        getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(DamageModule.class).getDamageType(),
        getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(DamageModule.class).getDamage());
  }
*/
  private boolean getDamage(Creature from, DamageType damageType, int damage) {
    return getModule(HPModule.class).changeHP(damage);
  }
  public int getArmorCoefficient(){
    return 10+getModule(SpecsModule.class).getAgilityModifier();
  }

  public String getName() {
    return name;
  }

  public CreatureRarity getRarity() {
    return rarity;
  }

  public Double getSpeed() {
    return (double) 200 + getModule(SpecsModule.class).getAgility() * 20;
  }

  public static class CreatureBuilder {

    private final HashMap<Class<? extends CreatureModule>, CreatureModule> modules;
    private final CreatureRarity rarity;       // Редкость персонажа [0;1]
    private final String name;
    private int level = 1;
    private HashMap<WeaponType, Integer> skills;

    public CreatureBuilder(CreatureRarity rarity, String name, int level) {
      modules = new HashMap<>();
      this.rarity = rarity;
      this.name = name;
      this.level = level;

      this.skills = new HashMap<>(Map.of(
          WeaponType.throwing, 0,
          WeaponType.melee, 0,
          WeaponType.fencing, 0,
          WeaponType.shooting, 0,
          WeaponType.magic, 0));
    }

    public CreatureBuilder(CreatureRarity rarity, String name) {
      modules = new HashMap<>();
      this.rarity = rarity;
      this.name = name;
      this.skills = new HashMap<>(Map.of(
          WeaponType.throwing, 0,
          WeaponType.melee, 0,
          WeaponType.fencing, 0,
          WeaponType.shooting, 0,
          WeaponType.magic, 0));
    }

    public CreatureBuilder setSkill(WeaponType skill, Integer value) {
      skills.remove(skill);
      skills.put(skill, value);
      return this;
    }

    public CreatureBuilder setSpecs(int hp, int strength, int agility, int durability,
        int intelligence, int wisdom, int charisma) {
      SpecsModule specsModule = new SpecsModule(strength, agility, durability, intelligence, wisdom,
          charisma);
      modules.put(SpecsModule.class, specsModule);
      HPModule hpModule = new HPModule(hp, specsModule);
      modules.put(HPModule.class, specsModule);
      return this;
    }

    public CreatureBuilder setStartWeapon(Weapons weaponType) {
      CreatureWeaponModule module = new CreatureWeaponModule(weaponType);
      modules.put(CreatureWeaponModule.class, module);
      return this;
    }

    public CreatureBuilder setID(int imageID) {
      JFXModule module = new JFXModule(imageID);
      modules.put(JFXModule.class, module);
      return this;
    }

    public Creature build() {
      WeaponSkillsModule module = new WeaponSkillsModule(skills);
      modules.put(WeaponSkillsModule.class, module);
      return new Creature(this);
    }
  }
}
