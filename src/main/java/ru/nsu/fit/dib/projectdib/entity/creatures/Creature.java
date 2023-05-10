package ru.nsu.fit.dib.projectdib.entity.creatures;

import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;

/**
 * Data
 */
public final class Creature {

  CreatureBuilder data;

  public Creature(CreatureBuilder creatureBuilder) {
    data = creatureBuilder;
  }

  private boolean getDamage(Creature from, DamageType damageType, int damage) {
    return false; //getModule(HPModule.class).changeHP(damage); todo доделать
  }

  public int getArmorCoefficient() {
    return 10 + getModifier("agility");
  }

  public String getName() {
    return data.name;
  }

  public CreatureRarity getRarity() {
    return data.type.getCreatureRarity();
  }

  public Double getSpeed() {
    return (double) 150 + getStat("agility") * 30;
  }

  public int getModifier(String stat) {
    return getStat(stat) / 2 - 5;
  }

  public int getStat(String stat) {
    return switch (stat) {
      case "strength" -> data.specs[0];
      case "agility" -> data.specs[1];
      case "durability" -> data.specs[2];
      case "intelligence" -> data.specs[3];
      case "wisdom" -> data.specs[4];
      case "charisma" -> data.specs[5];
      case default -> throw new IllegalStateException("Unexpected value: " + stat);
    };
  }
  public CreatureType getType() {
    return data.type;
  }
  public EntityType getEntityType() {
    return TypeChooser.getTypeByString(data.type.getName());
  }

  public int getMaxHP() {
    return data.hp;
  }

  public Weapons getStandardWeapon() {
    return data.weaponType;
  }
}
