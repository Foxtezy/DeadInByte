package ru.nsu.fit.dib.projectdib.data.json.spawn;

import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;

public class HeroJSON {

  private Integer id;
  private Integer seed;
  private HeroType heroType;

  public HeroType getHeroType() {
    return heroType;
  }

  public Integer getId() {
    return id;
  }
}
