package ru.nsu.fit.dib.projectdib.entity.creatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.geometry.Rectangle2D;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class HeroesFactory {
  public static Creature newHero(HeroType heroType, Integer seed) {
    List<Integer> specsList = RandomSystem.generateCreatureSpecs(seed);
    return switch (heroType) {
      case Elf -> new CreatureBuilder( "GigaElf",heroType)
          .setHP(RandomSystem.d(10))
          .setSpecs(
              specsList.get(3), specsList.get(0), specsList.get(2),
              specsList.get(5), specsList.get(4), specsList.get(1))
          .setWeapon(Weapons.Bow)
          .setSkill(WeaponType.shooting, 2)
          .build();
      case JonnyS -> new CreatureBuilder("Jonny",heroType)
          .setHP(RandomSystem.d(10))
          .setSpecs(
              specsList.get(4), specsList.get(0), specsList.get(1),
              specsList.get(3), specsList.get(5), specsList.get(2))
          .setWeapon(Weapons.AK47)
          .setSkill(WeaponType.shooting, 2).build();
      case Knight -> new CreatureBuilder( "Knight",heroType)
          .setHP(RandomSystem.d(10))
          .setSpecs(
              specsList.get(1), specsList.get(2), specsList.get(0),
              specsList.get(5), specsList.get(4), specsList.get(3))
          .setWeapon(Weapons.Sword)
          .setSkill(WeaponType.melee, 2)
          .build();
      case Wizard -> new CreatureBuilder("Wizard",heroType)
          .setHP(RandomSystem.d(6))
          .setSpecs(
              specsList.get(3), specsList.get(0), specsList.get(2),
              specsList.get(5), specsList.get(4), specsList.get(1)
          )
          .setWeapon(Weapons.Staff)
          .setSkill(WeaponType.magic, 2)
          .build();
      default -> throw new NullPointerException("Undeclared hero type");
    };
  }

  public enum HeroType implements CreatureType{
    Elf(CreatureRarity.mediocre, "elf",0,new Rectangle2D(0,130,160,120)),
    Knight(CreatureRarity.ordinary, "knight",1,new Rectangle2D(0,130+320,160,120)),
    Wizard(CreatureRarity.special, "wizard",2,new Rectangle2D(0,130+320*2,160,120)),
    JonnyS(CreatureRarity.masterful, "jonnys",3,new Rectangle2D(0,130+320*3,160,120));
    private final CreatureRarity creatureRarity;
    private final int imageID;
    private final Rectangle2D icon;

    public String getName() {
      return name;
    }

    private final String name;
    private static final Map<String, HeroType> map = Arrays.stream(values())
        .collect(
            Collectors.toMap(orderStatus -> orderStatus.name, orderStatus -> orderStatus));

    HeroType(CreatureRarity creatureRarity, String name, int imageID, Rectangle2D icon) {
      this.creatureRarity = creatureRarity;
      this.name = name;
      this.imageID = imageID;
      this.icon = icon;
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

    public int getID() {
      return imageID;
    }

    @Override
    public String getSubclass() {
      return "player";
    }

    public Rectangle2D getIcon() {
      return icon;
    }
  }
}
