package ru.nsu.fit.dib.projectdib.entity.creatures.modules;

import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;

public class SpecsModule extends CreatureModule {
  private final int strength;               // Сила

  public int getAgility() {
    return agility;
  }

  private final int agility;                // Ловскость
  private final int durability;             // Выносливость
  private final int intelligence;           // Интеллект
  private final int wisdom;                 // Мудрость
  private final int charisma;               // Харизма
  public SpecsModule(int strength, int agility, int durability,
      int intelligence, int wisdom, int charisma){
    this.strength = strength;
    this.agility = agility;
    this.durability = durability;
    this.intelligence = intelligence;
    this.wisdom = wisdom;
    this.charisma = charisma;
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
}
