package ru.nsu.fit.dib.projectdib.entity.components.fight;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.utils.CircularLinkedList;

public class WeaponInventoryComponent extends Component {
  private CircularLinkedList<Entity> inventory;
  private int activeWeapon;
  private int capacity;
  public WeaponInventoryComponent(int capacity) {
    this.capacity=capacity;
    inventory = new CircularLinkedList<>(capacity);
  }
  public Entity takeWeapon(Entity weapon) {
    Entity thrownWeapon = inventory.replaceCurrent(weapon);
    if (thrownWeapon!=null) {
      thrownWeapon.getComponent(DataComponent.class).setOwnerID(-1);
      thrownWeapon.getComponent(WeaponComponent.class).setUser(null);
    }
    weapon.getComponent(WeaponComponent.class).setUser(getEntity());
    weapon.getComponent(DataComponent.class).setOwnerID(getEntity().getComponent(DataComponent.class).getOwnerID());
    return thrownWeapon;
  }

  public void throwWeapon() {
    Entity thrownWeapon = inventory.replaceCurrent();
    thrownWeapon.getComponent(DataComponent.class).setOwnerID(-1);
    thrownWeapon.getComponent(WeaponComponent.class).setUser(null);
  }

  public void swapWeapon() {
    Entity last = inventory.getCurrent();
    Entity curr = inventory.next();
    if (last!=null) last.setVisible(false);
    if (curr!=null) curr.setVisible(true);
  }

  public Entity getActiveWeapon() {
    return inventory.getCurrent();
  }

  public List<Entity> findWeapon() {
    List<Entity> list = new ArrayList<>(
        getGameWorld().getEntitiesByType(EntityType.WEAPON).stream()
            .filter(weapon -> weapon.hasComponent(CollidableComponent.class) && weapon.isColliding(getEntity()))
            .filter(weapon -> !weapon.getComponent(WeaponComponent.class).hasUser()).toList());
    //Удаляем все оружие игрока из списка
    inventory.forEach(list::remove);
    return list;
  }

  public int size() {
    return inventory.size();
  }
}
