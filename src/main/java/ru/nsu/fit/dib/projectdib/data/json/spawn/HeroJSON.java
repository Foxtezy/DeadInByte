package ru.nsu.fit.dib.projectdib.data.json.spawn;

import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;

public class HeroJSON {

  Integer id;
  Integer seed;
  private HeroType heroType;

  public HeroType getHeroType() {
    return heroType;
  }

  public Integer getId() {
    return id;
  }
}
