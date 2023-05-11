package ru.nsu.fit.dib.projectdib.entity.components;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import java.util.Comparator;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;

public class DataAttackComponent extends Component {

  public int getAttack() {
    if (isWeapon) {
      if (!getEntity().hasComponent(WeaponComponent.class)
          || getEntity().getComponent(WeaponComponent.class).getUser() == null
          || !getEntity().getComponent(WeaponComponent.class).getUser().hasComponent(CreatureComponent.class)) {
        return 0;
      }

      Entity user = getEntity().getComponent(WeaponComponent.class).getUser();
      Creature userCreature = user.getComponent(CreatureComponent.class).getCreature();
      Integer modifier = getEntity().getComponent(WeaponComponent.class).getWeapon().getType().getWeaponType().getModifierList().stream().map(
          userCreature::getModifier).max(Comparator.naturalOrder()).get();
      int attack = RandomSystem.d(20)+
          modifier +
          userCreature.getWeaponLevel(getEntity().getComponent(WeaponComponent.class).getWeapon().getType()
              .getWeaponType());
      return attack;
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
      Entity user = getEntity().getComponent(WeaponComponent.class).getUser();
      Creature userCreature = user.getComponent(CreatureComponent.class).getCreature();
      var bounds = getEntity().getComponent(WeaponComponent.class).getWeapon().getDamageBounds();
      int damage = RandomSystem.getRandInt(bounds.getKey(),bounds.getValue())+
          userCreature.getWeaponLevel(getEntity().getComponent(WeaponComponent.class).getWeapon().getType()
              .getWeaponType());
      return damage;
    }
    return damage;
  }

  private boolean isWeapon = false;
  private Integer attack;
  private Integer damage;

  public DataAttackComponent(int attack, int damage) {
    this.attack = attack;
    this.damage = damage;
  }

  public DataAttackComponent() {
    isWeapon = true;
  }
}
