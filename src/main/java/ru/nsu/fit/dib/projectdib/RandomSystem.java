package ru.nsu.fit.dib.projectdib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RandomSystem {

  public static Random random = new Random();

  public static Integer getRandInt(int from, int to) {
    return from + random.nextInt(0, to);
  }

  public static Integer getRandInt(int to) {
    return random.nextInt(to);
  }

  public static Integer d(int numberCubeFaces) {
    return random.nextInt(numberCubeFaces + 1);
  }

  public static List<Integer> generateCreatureSpecs() {
    List<Integer> list = new ArrayList<>();
    for (int j = 0; j < 6; j++) {
      List<Integer> rolls = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
        rolls.add(d(6));
      }
      Collections.sort(rolls);
      int value = 0;
      for (int roll = 0; roll < rolls.size() - 1; roll++) {
        value += rolls.get(roll);
      }
      list.add(value);
    }
    Collections.sort(list);
    return list;
  }

  public static Double getRandDouble(int from, int to) {
    return random.nextDouble(from, to);
  }
}
