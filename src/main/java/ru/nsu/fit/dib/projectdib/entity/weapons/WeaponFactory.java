package ru.nsu.fit.dib.projectdib.entity.weapons;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponRarity;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponSize;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class WeaponFactory {

  public static Weapon getWeapon(Weapons weapon) {
    return switch (weapon) {
      case Hand -> new Weapon.WeaponBuilder(WeaponRarity.mediocre, "hand")
          .setDamageModule(WeaponType.shooting, DamageType.PIERCING, 0, 1)
          .setView(24, WeaponSize.MEDIUM, 0d, 0d)
          .setScope(100, 60)
          .setTime(300, 200)
          .build();
      case Bow -> new Weapon.WeaponBuilder(WeaponRarity.ordinary, "bow")
          .setDamageModule(WeaponType.shooting, DamageType.PIERCING, 1, 3)
          .setView(23, WeaponSize.MEDIUM, 0d, 40d)
          .setScope(1000, 5)
          .setTime(500, 200)
          .build();
      case AK47 -> new Weapon.WeaponBuilder(WeaponRarity.special, "ak47")
          .setDamageModule(WeaponType.shooting, DamageType.PIERCING, 1, 4)
          .setView(17, WeaponSize.MEDIUM, 90d, 150d)
          .setScope(1000, 1)
          .setTime(100, 500)
          .build();
      case Staff -> new Weapon.WeaponBuilder(WeaponRarity.ordinary, "staff")
          .setDamageModule(WeaponType.magic, DamageType.PIERCING, 1, 6)
          .setView(21, WeaponSize.MEDIUM, 80d, 40d)
          .setScope(1000, 1)
          .setTime(600, 300)
          .build();
      case Sword -> new Weapon.WeaponBuilder(WeaponRarity.ordinary, "sword")
          .setDamageModule(WeaponType.melee, DamageType.SLASHING, 3, 4)
          .setView(2, WeaponSize.MEDIUM, 90d, 150d)
          .setScope(50, 120)
          .setTime(200, 100)
          .build();
      case Rapier -> new Weapon.WeaponBuilder(WeaponRarity.ordinary, "rapier")
          .setDamageModule(WeaponType.fencing, DamageType.PIERCING, 1, 3)
          .setView(8, WeaponSize.MEDIUM, 90d, 150d)
          .setScope(50, 30)
          .setTime(100, 100)
          .build();
      default -> null;
    };
  }

  public enum Weapons {
    Hand("hand"),
    Rapier("rapier"),
    Sword("sword"),
    AK47("ak47"),
    Bow("bow"),
    Staff("staff");
    private static final Map<String, Weapons> map = Arrays.stream(values())
        .collect(
            Collectors.toMap(orderStatus -> orderStatus.name, orderStatus -> orderStatus));
    private final String name;
    Weapons(String name) {
      this.name=name;
    }
    public static Weapons getByName(String weaponName){
      return map.get(weaponName);
    }
  }
}