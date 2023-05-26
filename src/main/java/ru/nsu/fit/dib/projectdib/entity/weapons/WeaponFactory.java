package ru.nsu.fit.dib.projectdib.entity.weapons;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponRarity;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponSize;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class WeaponFactory {

  public static Weapon getWeapon(Weapons weapon) {
    return switch (weapon) {
      case Hand -> new WeaponBuilder("hand", Weapons.Hand)
          .setDamage( 0, 1)
          .setScope(100, 60)
          .setTime(300, 200)
          .build();
      case Bow -> new WeaponBuilder("bow", Weapons.Bow)
          .setDamage(1, 5)
          .setScope(1000, 5)
          .setTime(500, 200)
          .build();
      case AK47 -> new WeaponBuilder("ak47", Weapons.AK47)
          .setDamage( 1, 2)
          .setScope(1000, 1)
          .setTime(100, 500)
          .build();
      case Staff -> new WeaponBuilder("staff", Weapons.Staff)
          .setDamage( 1, 6)
          .setScope(1000, 1)
          .setTime(600, 300)
          .build();
      case Sword -> new WeaponBuilder("sword", Weapons.Sword)
          .setDamage( 2, 4)
          .setScope(50, 120)
          .setTime(200, 100)
          .build();
      case Cleaver -> new WeaponBuilder("cleaver", Weapons.Cleaver)
          .setDamage( 2, 6)
          .setScope(50, 120)
          .setTime(200, 100)
          .build();
      case Hatchet -> new WeaponBuilder("hatchet", Weapons.Hatchet)
          .setDamage( 2, 3)
          .setScope(50, 120)
          .setTime(200, 100)
          .build();
      case Rapier -> new WeaponBuilder("rapier", Weapons.Rapier)
          .setDamage( 2, 3)
          .setScope(50, 30)
          .setTime(100, 100)
          .build();
      case RustySword -> new WeaponBuilder("rusty sword", Weapons.RustySword)
          .setDamage( 2, 3)
          .setScope(50, 120)
          .setTime(200, 100)
          .build();
      case Katana -> new WeaponBuilder("katana", Weapons.Katana)
          .setDamage( 2, 2)
          .setScope(50, 30)
          .setTime(100, 100)
          .build();
      case GoldSword -> new WeaponBuilder("gold sword", Weapons.GoldSword)
          .setDamage( 2, 8)
          .setScope(50, 30)
          .setTime(100, 100)
          .build();
      case BrokenStaff -> new WeaponBuilder("broken staff", Weapons.GoldSword)
          .setDamage( 1, 2)
          .setScope(50, 30)
          .setTime(100, 100)
          .build();
      default -> null;
    };
  }

  public enum Weapons {
    BrokenStaff("broken staff",WeaponRarity.ordinary,WeaponType.melee, DamageType.SLASHING, 20, WeaponSize.MEDIUM, 90d, 150d),
    GoldSword("gold sword",WeaponRarity.ordinary,WeaponType.melee, DamageType.SLASHING, 10, WeaponSize.MEDIUM, 90d, 150d),
    Katana("katana",WeaponRarity.ordinary,WeaponType.melee, DamageType.SLASHING, 3, WeaponSize.MEDIUM, 90d, 150d),
    RustySword("rusty sword",WeaponRarity.ordinary,WeaponType.melee, DamageType.SLASHING, 0, WeaponSize.MEDIUM, 90d, 150d),
    Hatchet("hatchet",WeaponRarity.ordinary,WeaponType.melee, DamageType.SLASHING, 19, WeaponSize.MEDIUM, 90d, 150d),
    Cleaver("cleaver",WeaponRarity.ordinary,WeaponType.melee, DamageType.SLASHING, 4, WeaponSize.MEDIUM, 90d, 150d),
    Hand("hand",WeaponRarity.mediocre,WeaponType.shooting, DamageType.PIERCING, 24, WeaponSize.MEDIUM, 0d, 0d),
    Rapier("rapier",WeaponRarity.ordinary,WeaponType.melee, DamageType.PIERCING,8, WeaponSize.MEDIUM, 90d, 150d),
    Sword("sword",WeaponRarity.ordinary,WeaponType.melee, DamageType.SLASHING, 2, WeaponSize.MEDIUM, 90d, 150d),
    AK47("ak47",WeaponRarity.special,WeaponType.shooting, DamageType.PIERCING,17, WeaponSize.MEDIUM, 90d, 60d),
    Bow("bow",WeaponRarity.ordinary,WeaponType.shooting, DamageType.PIERCING,23, WeaponSize.MEDIUM, 0d, 40d),
    Staff("staff",WeaponRarity.ordinary,WeaponType.magic, DamageType.PIERCING,21, WeaponSize.MEDIUM, 80d, 40d);
    private static final Map<String, Weapons> map = Arrays.stream(values())
        .collect(
            Collectors.toMap(orderStatus -> orderStatus.name, orderStatus -> orderStatus));
    private final DamageType damageType;
    private final int id;
    private final WeaponSize size;
    private final double rotation;
    private final double imgRadius;
    private final WeaponType weaponType;

    public WeaponRarity getRarity() {
      return rarity;
    }

    private final WeaponRarity rarity;

    public String getName() {
      return name;
    }

    private final String name;
    Weapons(String name, WeaponRarity rarity, WeaponType weaponType, DamageType damageType, int id,
        WeaponSize size, double rotation, double imgRadius) {
      this.name=name;
      this.rarity=rarity;
      this.weaponType=weaponType;
      this.damageType=damageType;
      this.id = id;
      this.size = size;
      this.rotation = rotation;
      this.imgRadius = imgRadius;
    }
    public static Weapons getByName(String weaponName){
      return map.get(weaponName);
    }

    public DamageType getDamageType() {
      return damageType;
    }

    public WeaponType getWeaponType() {
      return weaponType;
    }

    public int getId() {
      return id;
    }

    public WeaponSize getSize() {
      return size;
    }

    public double getRotation() {
      return rotation;
    }

    public double getImgRadius() {
      return imgRadius;
    }
  }
}