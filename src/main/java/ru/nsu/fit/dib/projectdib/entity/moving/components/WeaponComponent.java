package ru.nsu.fit.dib.projectdib.entity.moving.components;

import com.almasb.fxgl.entity.component.Component;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;

public class WeaponComponent extends Component {

  private final Weapon weapon;

  public WeaponComponent(Weapon weapon) {
    this.weapon = weapon;
  }

  public Weapon getWeapon() {
    return weapon;
  }
}
