package ru.nsu.fit.dib.projectdib.entity.components;


import com.almasb.fxgl.entity.component.Component;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;

public class DataDamageComponent extends Component {

  public int getAttack() {
    if (isWeapon) {
      if (!getEntity().hasComponent(WeaponComponent.class)
          || getEntity().getComponent(WeaponComponent.class).getUser() == null
          || !getEntity().getComponent(WeaponComponent.class).getUser().hasComponent(CreatureComponent.class)) {
        return 0;
      }
      return getEntity().getComponent(WeaponComponent.class).getUser()
          .getComponent(CreatureComponent.class).getAttack();
    }
    return attack;
  }

  public int getDamage() {
    if (isWeapon) {
      if (!getEntity().hasComponent(WeaponComponent.class)
          || getEntity().getComponent(WeaponComponent.class).getUser() == null
          || !getEntity().getComponent(WeaponComponent.class).getUser().hasComponent(CreatureComponent.class)) {
        return 0;
      }
      return getEntity().getComponent(WeaponComponent.class).getUser()
          .getComponent(CreatureComponent.class).getDamage();
    }
    return damage;
  }

  private boolean isWeapon = false;
  private Integer attack;
  private Integer damage;

  public DataDamageComponent(int attack, int damage) {
    this.attack = attack;
    this.damage = damage;
  }

  public DataDamageComponent() {
    isWeapon = true;
  }
}
