package ru.nsu.fit.dib.projectdib.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.CreatureRarity;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;

public class RandomCharacterSystem {

  static double karma = Config.KARMA;
  private static HashMap<CreatureRarity, Double> probabilities = new HashMap<>(Map.of(
      CreatureRarity.mediocre,1.0,
      CreatureRarity.ordinary, 0.6,
      CreatureRarity.special, 0.4,
      CreatureRarity.masterful, 0.2,
      CreatureRarity.legendary, 0.05));

  public static HeroType NewCharacter() {
    recalculateProbabilities();
    double r = RandomSystem.getRandDouble(0,1);
    if (r<=probabilities.get(CreatureRarity.legendary))
      return generateCharacter(CreatureRarity.legendary);
    else if (r<=probabilities.get(CreatureRarity.masterful))
      return generateCharacter(CreatureRarity.masterful);
    else if (r<=probabilities.get(CreatureRarity.special))
      return generateCharacter(CreatureRarity.special);
    else if (r<=probabilities.get(CreatureRarity.ordinary))
      return generateCharacter(CreatureRarity.ordinary);
    else return generateCharacter(CreatureRarity.mediocre);

  }

  private static HeroType generateCharacter(CreatureRarity creatureRarity) {
    List<HeroType> heroTypeList = HeroType.getAll(creatureRarity);
    if (heroTypeList.size()<=0) return RandomCharacterSystem.NewCharacter();
    int k=RandomSystem.getRandInt(0, heroTypeList.size());
    return heroTypeList.get(k);
  }

  private static void recalculateProbabilities() {
    changeProbability(CreatureRarity.legendary,karma);
    changeProbability(CreatureRarity.masterful,karma);
    changeProbability(CreatureRarity.ordinary,karma);
    changeProbability(CreatureRarity.mediocre,karma);
  }
  private static void changeProbability(CreatureRarity creatureRarity, Double modifier){
    double curr = probabilities.get(creatureRarity);
    probabilities.remove(creatureRarity);
    probabilities.put(creatureRarity, curr+creatureRarity.getProbability()*modifier);
  }
}