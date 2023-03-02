package ru.nsu.fit.dib.projectdib.entity.creatures;

import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.Factory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public final class Creature {

  private final CreatureRarity rarity;  // Редкость персонажа [0;1]
  private final String name;            // Имя
  private final int imageID;            // ID внешности
  private final int level;                  // Уровень
  private final int hp;                     // Здоровье
  private final int strength;               // Сила
  private final int agility;                // Ловскость
  private final int durability;             // Выносливость
  private final int intelligence;           // Интеллект
  private final int wisdom;                 // Мудрость
  private final int charisma;               // Харизма
  private final HashMap<WeaponType, Integer> skills;
  private int currentHP;              // Текущее здоровье
  private int activeWeapon;
  private int weaponSize;
  private List<Weapon> weapons;
  private boolean alive;

  public Creature(CreatureBuilder creatureBuilder) {
    this.rarity = creatureBuilder.rarity;
    this.name = creatureBuilder.name;
    this.imageID = creatureBuilder.imageID;
    this.level = creatureBuilder.level;
    this.hp = creatureBuilder.hp;
    this.currentHP = this.hp;
    this.strength = creatureBuilder.strength;
    this.agility = creatureBuilder.agility;
    this.durability = creatureBuilder.durability;
    this.intelligence = creatureBuilder.intelligence;
    this.wisdom = creatureBuilder.wisdom;
    this.charisma = creatureBuilder.charisma;
    this.activeWeapon = 0;
    this.weaponSize = 2;
    this.weapons = new ArrayList<>();
    for (int i = 0; i < weaponSize; i++) {
      weapons.add(WeaponFactory.getWeapon(Weapons.Hand));
    }
    changeWeapon(creatureBuilder.startWeapon);
    this.alive = true;
    this.skills = creatureBuilder.skills;
    //linkedEntities = new ArrayList<>();
  }

  /*
  private final List<Entity> linkedEntities;
  public List<Entity> getLinkedEntities() {
    return linkedEntities;
  }
*/
  public int getActiveWeaponNumber() {
    return activeWeapon;
  }

  public List<Weapon> getWeaponsList() {
    return new ArrayList<>(weapons);
  }

  public Weapon changeWeapon(Weapon weapon) {
    Weapon x = weapons.remove(activeWeapon);
    weapons.add(activeWeapon, weapon);
    x.setUser(null);
    weapon.setUser(this);
    return x;
  }

  public Weapon getActiveWeapon() {
    return weapons.get(activeWeapon);
  }

  public Weapon getNextWeapon() {
    if (activeWeapon == weapons.size() - 1) {
      activeWeapon = 0;
    } else {
      activeWeapon++;
    }
    return weapons.get(activeWeapon);
  }

  public boolean hit(Creature creature) {
    return creature.getDamage(this, getActiveWeapon().getDamageType(),
        getActiveWeapon().getDamage());
  }

  private boolean getDamage(Creature from, DamageType damageType, int damage) {
    return changeHP(damage);
  }

  public int getCurrentHP() {
    return currentHP;
  }

  public boolean changeHP(int damage) {
    if (currentHP > damage) {
      currentHP -= damage;
    } else {
      currentHP = 0;
      alive = false;
    }
    return alive;
  }

  public void setSkill(WeaponType weaponType, int value) {
    skills.remove(weaponType);
    skills.put(weaponType, value);
  }

  public int getSkills(WeaponType weaponType) {
    return skills.get(weaponType);
  }

  public int getStrengthModifier() {
    return strength / 2 - 5;
  }

  public int getAgilityModifier() {
    return agility / 2 - 5;
  }

  public int getDurabilityModifier() {
    return durability / 2 - 5;
  }

  public int getIntelligenceModifier() {
    return intelligence / 2 - 5;
  }

  public int getWisdomModifier() {
    return wisdom / 2 - 5;
  }

  public int getCharismaModifier() {
    return charisma / 2 - 5;
  }

  public String getName() {
    return name;
  }

  public int getImageID() {
    return imageID;
  }

  public CreatureRarity getRarity() {
    return rarity;
  }

  public Double getSpeed() {
    return (double) (getAgilityModifier() * 80);
  }

  public static class CreatureBuilder {

    private final CreatureRarity rarity;       // Редкость персонажа [0;1]
    private final String name;
    private final int imageID;
    private int level = 1;
    private HashMap<WeaponType, Integer> skills;
    private int hp;           // Здоровье
    private int strength;     // Сила
    private int agility;      // Ловскость
    private int durability;   // Выносливость
    private int intelligence; // Интеллект
    private int wisdom;       // Мудрость
    private int charisma;     // Харизма
    private Weapon startWeapon;

    public CreatureBuilder(CreatureRarity rarity, String name, int level, int imageID) {
      this.rarity = rarity;
      this.name = name;
      this.imageID = imageID;
      this.level = level;

      this.skills = new HashMap<>(Map.of(
          WeaponType.throwing, 0,
          WeaponType.melee, 0,
          WeaponType.fencing, 0,
          WeaponType.shooting, 0,
          WeaponType.magic, 0));
    }

    public CreatureBuilder(CreatureRarity rarity, String name, int imageID) {
      this.rarity = rarity;
      this.name = name;
      this.imageID = imageID;
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
        int intelligence,
        int wisdom, int charisma) {
      this.hp = hp + durability;
      this.strength = strength;
      this.agility = agility;
      this.durability = durability;
      this.intelligence = intelligence;
      this.wisdom = wisdom;
      this.charisma = charisma;
      return this;
    }

    public CreatureBuilder setStartWeapon(Weapon weapon) {
      startWeapon = weapon;
      return this;
    }

    public Creature build() {
      return new Creature(this);
    }
  }
}
