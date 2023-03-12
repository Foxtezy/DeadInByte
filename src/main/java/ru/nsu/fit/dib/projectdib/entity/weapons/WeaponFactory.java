package ru.nsu.fit.dib.projectdib.entity.weapons;

import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponRarity;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponSize;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class WeaponFactory {

  public static Weapon getWeapon(Weapons weapon) {
    return switch (weapon) {
      case Hand -> new Weapon.WeaponBuilder(WeaponRarity.mediocre, "hand")
          .setTypes(WeaponType.shooting, DamageType.PIERCING)
          .setView(24, WeaponSize.MEDIUM,0d,0d)
          .setDamage(0, 1)
          .setScope(100, 60)
          .setTime(300, 200)
          .build();
      case Bow -> new Weapon.WeaponBuilder(WeaponRarity.ordinary, "bow")
          .setTypes(WeaponType.shooting, DamageType.PIERCING)
          .setView(23, WeaponSize.MEDIUM,0d,40d)
          .setDamage(1, 3)
          .setScope(1000, 5)
          .setTime(500, 200)
          .build();
      case AK47 -> new Weapon.WeaponBuilder(WeaponRarity.special, "ak47")
          .setTypes(WeaponType.shooting, DamageType.PIERCING)
          .setView(17, WeaponSize.MEDIUM,90d,150d)
          .setDamage(1, 4)
          .setScope(1000, 1)
          .setTime(100, 500)
          .build();
      case Staff -> new Weapon.WeaponBuilder(WeaponRarity.ordinary, "staff")
          .setTypes(WeaponType.magic, DamageType.PIERCING)
          .setView(21, WeaponSize.MEDIUM,80d,50d)
          .setDamage(1, 6)
          .setScope(1000, 1)
          .setTime(600, 300)
          .build();
      case Sword -> new Weapon.WeaponBuilder(WeaponRarity.ordinary, "sword")
          .setTypes(WeaponType.melee, DamageType.SLASHING)
          .setView(2, WeaponSize.MEDIUM,90d,150d)
          .setDamage(3, 4)
          .setScope(50, 120)
          .setTime(200, 100)
          .build();
      case Rapier -> new Weapon.WeaponBuilder(WeaponRarity.ordinary, "rapier")
          .setTypes(WeaponType.fencing, DamageType.PIERCING)
          .setView(8, WeaponSize.MEDIUM,90d,150d)
          .setDamage(1, 3)
          .setScope(50, 30)
          .setTime(100, 100)
          .build();
      default -> null;
    };
  }

  public enum Weapons {
    Hand,
    Rapier,
    Sword,
    AK47,
    Bow,
    Staff
  }
}