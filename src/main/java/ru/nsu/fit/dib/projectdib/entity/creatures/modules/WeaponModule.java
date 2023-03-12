package ru.nsu.fit.dib.projectdib.entity.creatures.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class WeaponModule extends CreatureModule{
  private int activeWeapon;
  private int weaponSize;
  private List<Weapon> weapons;

  public WeaponModule(Weapon startWeapon){
    this.activeWeapon = 0;
    this.weaponSize = 2;
    this.weapons = new ArrayList<>();
    for (int i = 0; i < weaponSize; i++) {
      weapons.add(WeaponFactory.getWeapon(Weapons.Hand));
    }
    changeWeapon(startWeapon);
    user=null;
  }
  private Creature user;
  public void setWeaponModuleUser(Creature user){
    this.user=user;
    getActiveWeapon().setUser(user);
  };

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
    weapon.setUser(user);
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
}
