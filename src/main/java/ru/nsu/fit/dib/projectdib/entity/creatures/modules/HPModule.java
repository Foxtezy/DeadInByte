package ru.nsu.fit.dib.projectdib.entity.creatures.modules;

public class HPModule extends CreatureModule{
  private int hp;                     // Здоровье
  private int currentHP;              // Текущее здоровье
  private boolean alive;
  public HPModule(int hp, SpecsModule specsModule){
    hp = hp + specsModule.getDurabilityModifier()*2;
    currentHP = hp;
    alive = true;
  }
  public int getCurrentHP() {
    return currentHP;
  }

  public boolean changeHP(int damage) {
    if (currentHP > damage) {
      currentHP -= damage;
    } else {
      currentHP = 0;
      alive = false;
    }
    return alive;
  }
}
