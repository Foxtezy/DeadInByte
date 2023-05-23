package ru.nsu.fit.dib.projectdib.data;

import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.CreatureBuilder;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class HeroData {

  public HeroType getType() {
    return type;
  }

  public Integer getSeed() {
    return seed;
  }

  private final HeroType type;

  private final Integer seed;

  public HeroData(HeroType type, Integer seed) {
    this.type = type;
    this.seed=seed;
  }
}