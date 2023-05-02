package ru.nsu.fit.dib.projectdib.entity.weapons.enums;

import static ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType.rangeAction.HandToHand;
import static ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType.rangeAction.LongRange;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.SpecsModule;

public enum WeaponType {
  throwing(LongRange,0, List.of("strength")),
  shooting(LongRange,1,List.of("agility")),
  fencing(HandToHand,2,List.of("wisdom","intelligence")),
  melee(HandToHand,3,List.of("strength")),
  magic(LongRange,4,List.of("strength","agility"));
  private static final Map<Integer, WeaponType> map = Arrays.stream(values())
      .collect(
          Collectors.toMap(orderStatus -> orderStatus.id, orderStatus -> orderStatus));
  private final rangeAction longRange;
  private final int id;
  private final List<String> modifierList;
  WeaponType(rangeAction longRange, int id, List<String> modifierList) {
    this.longRange = longRange;
    this.id = id;
    this.modifierList=modifierList;
  }

  public static WeaponType getSkillByNumber(int id) {
    return map.get(id);
  }

  public rangeAction getLongRange() {
    return longRange;
  }

  public List<String> getModifierList() {
    return modifierList;
  }

  enum rangeAction {
    LongRange,
    HandToHand
  }
}

