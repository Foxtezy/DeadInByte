package ru.nsu.fit.dib.projectdib.entity.creatures;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class HeroesFactory {

  public static Creature getHero(HeroType heroType) {
    List<Integer> specsList = RandomSystem.generateCreatureSpecs();
    return switch (heroType) {
      case Elf -> new Creature.CreatureBuilder(CreatureRarity.mediocre, "GigaElf")
          .setID(0)
          .setSpecs(
              RandomSystem.d(10),
              specsList.get(3), specsList.get(0), specsList.get(2),
              specsList.get(5), specsList.get(4), specsList.get(1))
          .setStartWeapon(WeaponFactory.getWeapon(Weapons.Bow))
          .setSkill(WeaponType.shooting, 2)
          .build();
      case JonnyS -> new Creature.CreatureBuilder(CreatureRarity.masterful, "Jonny")
          .setID(3)
          .setSpecs(
              RandomSystem.d(10),
              specsList.get(4), specsList.get(0), specsList.get(1),
              specsList.get(3), specsList.get(5), specsList.get(2))
          .setStartWeapon(WeaponFactory.getWeapon(Weapons.AK47))
          .setSkill(WeaponType.shooting, 2).build();
      case Knight -> new Creature.CreatureBuilder(CreatureRarity.ordinary, "Knight")
          .setID(1)
          .setSpecs(
              RandomSystem.d(10),
              specsList.get(1), specsList.get(2), specsList.get(0),
              specsList.get(5), specsList.get(4), specsList.get(3))
          .setStartWeapon(WeaponFactory.getWeapon(Weapons.Sword))
          .setSkill(WeaponType.melee, 2)
          .build();
      case Wizard -> new Creature.CreatureBuilder(CreatureRarity.special, "Wizard")
          .setID(2)
          .setSpecs(
              RandomSystem.d(6),
              specsList.get(3), specsList.get(0), specsList.get(2),
              specsList.get(5), specsList.get(4), specsList.get(1)
          )
          .setStartWeapon(WeaponFactory.getWeapon(Weapons.Staff))
          .setSkill(WeaponType.magic, 2)
          .build();
    };
  }

  public enum HeroType {
    Elf(CreatureRarity.mediocre),
    Knight(CreatureRarity.ordinary),
    Wizard(CreatureRarity.special),
    JonnyS(CreatureRarity.masterful);
    final CreatureRarity creatureRarity;

    HeroType(CreatureRarity creatureRarity) {
      this.creatureRarity = creatureRarity;
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
