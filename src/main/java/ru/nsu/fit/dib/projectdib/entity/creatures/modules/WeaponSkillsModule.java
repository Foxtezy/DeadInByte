package ru.nsu.fit.dib.projectdib.entity.creatures.modules;

import java.util.HashMap;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

public class WeaponSkillsModule extends CreatureModule{
  private final HashMap<WeaponType, Integer> skills;

  public WeaponSkillsModule(HashMap<WeaponType, Integer> skills){
    this.skills = skills;
  }
  public int getSkills(WeaponType weaponType) {
    return skills.get(weaponType);
  }

  public void setSkill(WeaponType weaponType, int value) {
    skills.remove(weaponType);
    skills.put(weaponType, value);
  }
}
