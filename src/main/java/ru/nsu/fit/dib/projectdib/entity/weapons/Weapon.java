package ru.nsu.fit.dib.projectdib.entity.weapons;

import java.util.HashMap;

import java.util.Map;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponRarity;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponSize;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.DamageModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.SquareDamageModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TimingsModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.WeaponModule;

public class Weapon {

  protected Creature user;
  private Map<Class<? extends WeaponModule>, ? extends WeaponModule> modules;
  private WeaponRarity rarity;// Редкость персонажа [0;1]
  private final String name;

  public Weapon(Creature user, Map<Class<? extends WeaponModule>, ? extends WeaponModule> modules,
      WeaponRarity rarity, String name) {
    this.user = user;
    this.modules = modules;
    this.rarity = rarity;
    this.name = name;
  }

  public Weapon(WeaponBuilder weaponBuilder) {
    modules = weaponBuilder.modules;
    this.rarity = weaponBuilder.rarity;
    this.name = weaponBuilder.name;
  }

  public <T extends WeaponModule> T getModule(Class<T> type) {
    return (T) modules.get(type);
  }

  public Creature getUser() {
    return user;
  }

  public void setUser(Creature user) {
    this.user = user;
    getModule(DamageModule.class).setUser(user);
  }

  public Map<Class<? extends WeaponModule>, ? extends WeaponModule> getModules() {
    return modules;
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

  public static class WeaponBuilder {

    private final Map<Class<? extends WeaponModule>, WeaponModule> modules;
    private final WeaponRarity rarity;
    private final String name;

    public WeaponBuilder(WeaponRarity rarity, String name) {
      modules = new HashMap<>();
      this.rarity = rarity;
      this.name = name;
    }

    public WeaponBuilder setDamageModule(WeaponType weaponType, DamageType damageType,
        int minDamage, int maxDamage) {
      DamageModule module = new DamageModule(weaponType, damageType, minDamage, maxDamage);
      modules.put(DamageModule.class, module);
      return this;
    }

    public WeaponBuilder setView(int weaponID, WeaponSize weaponSize, double angle,
        double imgRadius) {
      TextureModule module = new TextureModule(weaponID, weaponSize, angle, imgRadius);
      modules.put(TextureModule.class, module);
      return this;
    }

    public WeaponBuilder setScope(double distance, double radius) {
      SquareDamageModule module = new SquareDamageModule(distance, radius);
      modules.put(SquareDamageModule.class, module);
      return this;
    }

    public WeaponBuilder setTime(int rollbackTime, int impactSpeed) {
      TimingsModule module = new TimingsModule(rollbackTime, impactSpeed);
      modules.put(TimingsModule.class, module);
      return this;
    }

    public Weapon build() {
      return new Weapon(this);
    }
  }
}
