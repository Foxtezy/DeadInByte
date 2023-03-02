package ru.nsu.fit.dib.projectdib.entity.creatures;

public enum CreatureRarity {
  mediocre(0.3),
  ordinary(0.45),
  special(0.20),
  masterful(0.45),
  legendary(0.05);
  private final double probability;

  CreatureRarity(double probability) {
    this.probability = probability;
  }

  ;

  public double getProbability() {
    return probability;
  }

}
