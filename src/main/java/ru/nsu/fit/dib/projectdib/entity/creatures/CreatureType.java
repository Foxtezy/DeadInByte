package ru.nsu.fit.dib.projectdib.entity.creatures;

import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;

public interface CreatureType {
  String getName();
  CreatureRarity getCreatureRarity();
  int getID();

  String getSubclass();
}
