package ru.nsu.fit.dib.projectdib.entity.creatures;

public enum CreatureRarity {
  ordinary(0.45),
  masterful(0.45),
  mediocre(0.3),
  special(0.20),
  legendary(0.05);
  private final double probability;

  CreatureRarity(double probability) {
    this.probability = probability;
  }

  public double getProbability() {
    return probability;
  }

}
