package ru.nsu.fit.dib.projectdib.entity.components.fight;

import static com.almasb.fxgl.dsl.FXGLForKtKt.newLocalTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import java.util.Map;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;

public class ShootAttackComponent extends Component {

  private LocalTimer shootTimer = newLocalTimer();
  int rollback = 0;

  public void shoot() {
    if (!getEntity().getComponent(WeaponInventoryComponent.class).getActiveWeapon().getComponent(
        WeaponComponent.class).getWeapon().isLongRange()) {
      return;
    }
    Entity weapon = getEntity().getComponent(WeaponInventoryComponent.class).getActiveWeapon();
    if (!shootTimer.elapsed(Duration.millis(rollback))) {
      return;
    }
    rollback = weapon.getComponent(WeaponComponent.class).getWeapon().getRollbackTime();
    SpawnData sd = new SpawnData(getEntity().getPosition().getX() + 20, getEntity().getPosition().getY() + 30);
    sd.put("projectileType",projectileMap.get(weapon.getComponent(WeaponComponent.class).getWeapon().getType()));
    spawn("projectile", sd);
    shootTimer.capture();
  }

  private static final Map<Weapons, Projectiles> projectileMap = Map.of(
      Weapons.Bow, Projectiles.ARROW,
      Weapons.AK47, Projectiles.BULLET,
      Weapons.Staff, Projectiles.FIREBALL);
}
