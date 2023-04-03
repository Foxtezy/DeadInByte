package ru.nsu.fit.dib.projectdib.entity.creatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class HeroesFactory {

  public static Creature newHero(HeroType heroType, Integer seed) {
    List<Integer> specsList = RandomSystem.generateCreatureSpecs(seed);
    return switch (heroType) {
      case Elf -> new Creature.CreatureBuilder(CreatureRarity.mediocre, "GigaElf")
          .setID(0)
          .setSpecs(
              RandomSystem.d(10),
              specsList.get(3), specsList.get(0), specsList.get(2),
              specsList.get(5), specsList.get(4), specsList.get(1))
          .setStartWeapon(Weapons.Bow)
          .setSkill(WeaponType.shooting, 2)
          .build();
      case JonnyS -> new Creature.CreatureBuilder(CreatureRarity.masterful, "Jonny")
          .setID(3)
          .setSpecs(
              RandomSystem.d(10),
              specsList.get(4), specsList.get(0), specsList.get(1),
              specsList.get(3), specsList.get(5), specsList.get(2))
          .setStartWeapon(Weapons.AK47)
          .setSkill(WeaponType.shooting, 2).build();
      case Knight -> new Creature.CreatureBuilder(CreatureRarity.ordinary, "Knight")
          .setID(1)
          .setSpecs(
              RandomSystem.d(10),
              specsList.get(1), specsList.get(2), specsList.get(0),
              specsList.get(5), specsList.get(4), specsList.get(3))
          .setStartWeapon(Weapons.Sword)
          .setSkill(WeaponType.melee, 2)
          .build();
      case Wizard -> new Creature.CreatureBuilder(CreatureRarity.special, "Wizard")
          .setID(2)
          .setSpecs(
              RandomSystem.d(6),
              specsList.get(3), specsList.get(0), specsList.get(2),
              specsList.get(5), specsList.get(4), specsList.get(1)
          )
          .setStartWeapon(Weapons.Staff)
          .setSkill(WeaponType.magic, 2)
          .build();
      default -> throw new NullPointerException("Undeclared hero type");
    };
  }

  public enum HeroType {
    Elf(CreatureRarity.mediocre, "elf"),
    Knight(CreatureRarity.ordinary, "knight"),
    Wizard(CreatureRarity.special, "wizard"),
    JonnyS(CreatureRarity.masterful, "jonnys");
    private final CreatureRarity creatureRarity;

    public String getName() {
      return name;
    }

    private final String name;
    private static final Map<String, HeroType> map = Arrays.stream(values())
        .collect(
            Collectors.toMap(orderStatus -> orderStatus.name, orderStatus -> orderStatus));

    HeroType(CreatureRarity creatureRarity, String name) {
      this.creatureRarity = creatureRarity;
      this.name = name;
    }

    public static HeroType getByName(String findingName) {
      return map.get(findingName);
    }

    public static List<HeroType> getAll(CreatureRarity rarity) {
      List<HeroType> heroTypes = new ArrayList<>();
      for (HeroType heroType : HeroType.values()) {
        if (heroType.getCreatureRarity() == rarity) {
          heroTypes.add(heroType);
        }
      }
      return heroTypes;
    }

    public CreatureRarity getCreatureRarity() {
      return creatureRarity;
    }

  }
}
