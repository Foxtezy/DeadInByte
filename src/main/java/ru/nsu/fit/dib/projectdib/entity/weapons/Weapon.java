package ru.nsu.fit.dib.projectdib.entity.weapons;

import com.almasb.fxgl.entity.Entity;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.data.ProjectConfig;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.moving.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponRarity;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponSize;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class Weapon {

  public WeaponComponent component;
  protected int impactSpeed;
  protected Creature user;
  protected int weaponID;
  protected WeaponSize weaponSize;
  protected WeaponType weaponType;
  protected DamageType damageType;
  protected int minDamage;
  protected int maxDamage;
  protected double distance;
  protected double radius;
  protected int rollbackTime;
  private WeaponRarity rarity;// Редкость персонажа [0;1]
  private String name;
  public Weapon(WeaponBuilder weaponBuilder) {
    this.rarity = weaponBuilder.rarity;
    this.name = weaponBuilder.name;
    this.weaponType = weaponBuilder.weaponType;
    this.damageType = weaponBuilder.damageType;
    this.weaponID = weaponBuilder.weaponID;
    this.weaponSize = weaponBuilder.weaponSize;
    this.minDamage = weaponBuilder.minDamage;
    this.maxDamage = weaponBuilder.maxDamage;
    this.distance = weaponBuilder.distance;
    this.radius = weaponBuilder.radius;
    this.rollbackTime = weaponBuilder.rollbackTime;
    this.impactSpeed = weaponBuilder.impactSpeed;
  }

  public WeaponComponent getComponent() {
    return component;
  }

  public void setComponent(WeaponComponent component) {
    this.component = component;
  }

  public int getWeaponID() {
    return weaponID;
  }

  public void setUser(Creature user) {
    this.user = user;
  }

  public int getAttack() {
    return RandomSystem.d(20) + user.getSkills(weaponType) +
        switch (weaponType) {
          case melee -> user.getStrengthModifier();
          case shooting -> user.getAgilityModifier();
          case magic -> Math.max(user.getWisdomModifier(), user.getIntelligenceModifier());
          case fencing, throwing -> Math.max(user.getStrengthModifier(), user.getAgilityModifier());
        };
  }

  public int getDamage() {
    return RandomSystem.getRandInt(minDamage, maxDamage) + switch (weaponType) {
      case melee, shooting -> user.getStrengthModifier();
      case fencing, throwing -> Math.max(user.getStrengthModifier(), user.getAgilityModifier());
      case magic -> Math.max(user.getWisdomModifier(), user.getIntelligenceModifier());
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

  protected double getDistance() {
    return distance;
  }

  protected void setDistance(double distance) {
    this.distance = distance;
  }

  protected double getRadius() {
    return radius;
  }

  protected void setRadius(double radius) {
    this.radius = radius;
  }

  protected int getRollbackTime() {
    return rollbackTime;
  }

  protected void setRollbackTime(int rollbackTime) {
    this.rollbackTime = rollbackTime;
  }

  public WeaponRarity getRarity() {
    return rarity;
  }

  protected void setRarity(WeaponRarity rarity) {
    this.rarity = rarity;
  }

  public String getName() {
    return name;
  }

  public String getTexturePath() {
    return switch (weaponSize) {
      case SMALL -> ProjectConfig._smallWeapon;
      case MEDIUM -> ProjectConfig._mediumWeapon;
      case BIG -> ProjectConfig._bigWeapon;
    };
  }

  public Integer getImageWidht() {
    return weaponSize.getWidth();
  }

  ;

  public int getImageHeight() {
    return weaponSize.getHeight();
  }

  public static class WeaponBuilder {

    private int impactSpeed;
    private Creature user;
    private int weaponID;
    private WeaponSize weaponSize;
    private WeaponType weaponType;
    private DamageType damageType;
    private int minDamage;
    private int maxDamage;
    private double distance;
    private double radius;
    private int rollbackTime;
    private WeaponRarity rarity;// Редкость персонажа [0;1]
    private String name;
    public WeaponBuilder(WeaponRarity rarity, String name) {
      this.rarity = rarity;
      this.name = name;
    }

    public int getWeaponID() {
      return weaponID;
    }

    public WeaponBuilder setTypes(WeaponType weaponType, DamageType damageType) {
      this.weaponType = weaponType;
      this.damageType = damageType;
      return this;
    }

    public WeaponBuilder setView(int weaponID, WeaponSize weaponSize) {
      this.weaponID = weaponID;
      this.weaponSize = weaponSize;
      return this;
    }

    public WeaponBuilder setDamage(int minDamage, int maxDamage) {
      this.minDamage = minDamage;
      this.maxDamage = maxDamage;
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
}
