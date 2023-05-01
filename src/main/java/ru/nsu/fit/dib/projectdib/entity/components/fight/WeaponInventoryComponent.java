package ru.nsu.fit.dib.projectdib.entity.components.fight;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.utils.CircularLinkedList;

public class WeaponInventoryComponent extends Component {
  private CircularLinkedList<Entity> inventory;
  private int activeWeapon;
  private int capacity;
  public WeaponInventoryComponent(int capacity) {
    this.capacity=capacity;
    inventory = new CircularLinkedList<>(capacity);
  }
  public void takeWeapon(Entity weapon) {
    Entity thrownWeapon = inventory.replaceCurrent(weapon);
    thrownWeapon.getComponent(WeaponComponent.class).setUser(null);
    weapon.getComponent(WeaponComponent.class).setUser(getEntity());
  }

  public void throwWeapon() {
    Entity thrownWeapon = inventory.replaceCurrent();
    thrownWeapon.getComponent(WeaponComponent.class).setUser(null);
  }

  public void swapWeapon() {
    Entity last = inventory.getCurrent();
    Entity curr = inventory.next();
    if (last!=null) last.setVisible(false);
    if (curr!=null) curr.setVisible(false);
  }

  public Entity getActiveWeapon() {
    return inventory.getCurrent();
  }

  public void nextWeapon() {
    inventory.next();
  }
}
