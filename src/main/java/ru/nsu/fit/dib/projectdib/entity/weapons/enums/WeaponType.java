package ru.nsu.fit.dib.projectdib.entity.weapons.enums;

import static ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType.rangeAction.HandToHand;
import static ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType.rangeAction.LongRange;

public enum WeaponType {
  throwing(LongRange),
  shooting(LongRange),
  fencing(HandToHand),
  melee(HandToHand),
  magic(LongRange);

  final rangeAction longRange;

  WeaponType(rangeAction longRange) {
    this.longRange = longRange;
  }

  public rangeAction getLongRange() {
    return longRange;
  }

  enum rangeAction {
    LongRange,
    HandToHand
  }
}

