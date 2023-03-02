package ru.nsu.fit.dib.projectdib.entity.weapons.enums;

public enum WeaponRarity {
  mediocre(0.3),
  ordinary(0.45),
  special(0.20),
  masterful(0.45),
  legendary(0.05);
  private final double probability;

  WeaponRarity(double probability) {
    this.probability = probability;
  }

  ;

  public double getProbability() {
    return probability;
  }

}
