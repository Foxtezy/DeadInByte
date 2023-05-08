package ru.nsu.fit.dib.projectdib.entity.components;


import com.almasb.fxgl.entity.component.Component;

public class DataProjectileComponent extends Component {

  public int getAttack() {
    return attack;
  }

  public int getDamage() {
    return damage;
  }

  private final int attack;
  private final int damage;

  public DataProjectileComponent(int attack, int damage){

    this.attack = attack;
    this.damage = damage;
  }
}
