package ru.nsu.fit.dib.projectdib.entity.components.fight;

import static com.almasb.fxgl.dsl.FXGLForKtKt.newLocalTimer;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.components.DataAttackComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;

public class ShootAttackComponent extends Component {

  private LocalTimer shootTimer = newLocalTimer();
  int rollback = 0;

  public void shoot() {
    if (!getEntity().hasComponent(WeaponInventoryComponent.class) ||
        getEntity().getComponent(WeaponInventoryComponent.class).getActiveWeapon()==null ||
        !getEntity().getComponent(WeaponInventoryComponent.class).getActiveWeapon().getComponent(
        WeaponComponent.class).getWeapon().isLongRange()) {
      return;
    }
    Entity weapon = getEntity().getComponent(WeaponInventoryComponent.class).getActiveWeapon();
    if (!shootTimer.elapsed(Duration.millis(rollback))) {
      return;
    }
    Point2D rotation = getEntity().getComponent(DataComponent.class).getRotation();
    Point2D offsetVector = new Point2D(rotation.getY(),-rotation.getX()).normalize().multiply(40).add(-80,80);
    rollback = weapon.getComponent(WeaponComponent.class).getWeapon().getRollbackTime();
    int attack=weapon.getComponent(DataAttackComponent.class).getAttack();
    int damage=weapon.getComponent(DataAttackComponent.class).getDamage();
    EntitySpawner.spawn(new NewEntity(projectileMap.get(
        weapon.getComponent(WeaponComponent.class).getWeapon().getType()).getName(),
        attack*1000+damage, new EntityState(-1,
        getEntity().getAnchoredPosition().add(offsetVector),
        getEntity().getComponent(DataComponent.class).getRotation(),
        getEntity().getComponent(DataComponent.class).getId())));
    shootTimer.capture();
  }

  private static final Map<Weapons, Projectiles> projectileMap = Map.of(
      Weapons.Bow, Projectiles.ARROW,
      Weapons.AK47, Projectiles.BULLET,
      Weapons.Staff, Projectiles.FIREBALL);
}
