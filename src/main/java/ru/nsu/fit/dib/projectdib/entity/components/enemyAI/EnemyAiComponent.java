package ru.nsu.fit.dib.projectdib.entity.components.enemyAI;

import static com.almasb.fxgl.core.math.FXGLMath.sqrt;
import static com.almasb.fxgl.dsl.FXGL.geto;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.lengthOfCell;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.rangeOfVision;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.astar.AStarPathfinder;
import com.google.javascript.jscomp.jarjar.javax.annotation.CheckForNull;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.PlayerChaseComponent;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.ShootAttackComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.WeaponInventoryComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.CreatureViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.EnemyViewComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponViewComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;

public class EnemyAiComponent extends Component {
  AStar aStar = new AStar(new LazyValue<>(() -> geto("grid")));
  private Entity currentEnemy;


  @Override
  public void onAdded() {
    currentEnemy = getEntity();
    currentEnemy.addComponent(aStar);
  }

  @Override
  public void onUpdate(double tpf) {
    if(currentEnemy.getComponent(WeaponInventoryComponent.class).getActiveWeapon() == null){
      var weaponNearest = findNearestWeaponCurrentType(WeaponType.melee);
      if(weaponNearest == null){
        currentEnemy.getComponent(AStar.class).stopMovement();
        return;
      }else{
        if(!currentEnemy.isColliding(weaponNearest)){
          currentEnemy.getComponent(CellMove.class).moveToCell(weaponNearest.call("getCellX"), weaponNearest.call("getCellY"));
          return;
        } else {
          EntitySpawner.doAction(new Pair<>(MessageType.WEAPON,
              new WeaponAction(WeaponAction.WeaponActionType.TAKE,
                  currentEnemy.getComponent(DataComponent.class).getId(),
                  weaponNearest.getComponent(DataComponent.class).getId())));
          return;
        }
      }
    }

    Entity target = findNearestEntity(EntityType.PLAYER);

    if (target == null) {
      currentEnemy.getComponent(AStar.class).stopMovement();
      return;
    }else{
      if(target.distance(currentEnemy) >= rangeOfVision){
        currentEnemy.getComponent(AStar.class).stopMovement();
        return;
      }else{
        Point2D vectorView = new Point2D(target.getPosition().getX()- currentEnemy.getPosition().getX(), target.getPosition().getY()-currentEnemy.getPosition().getY());
        if(target.distance(currentEnemy) <= 1 * lengthOfCell){
          currentEnemy.getComponent(ShootAttackComponent.class).attack(vectorView);
            if(target.distance(currentEnemy) >= lengthOfCell){
              currentEnemy.getComponent(AStar.class).moveToCell(target.call("getCellX"), target.call("getCellY"));
            }
            currentEnemy.getComponent(AStar.class).stopMovement();
          return;
        }else{
          currentEnemy.getComponent(EnemyViewComponent.class).bindDirectionView(entity -> vectorView);
          currentEnemy.getComponent(AStar.class).moveToCell(target.call("getCellX"), target.call("getCellY"));
          return;
        }
      }
    }
  }

  @CheckForNull
  private Entity findNearestEntity(EntityType type) {
    List<Entity> heroList = FXGL.getGameWorld().getEntitiesByType(type);
    heroList = heroList.stream()
        .filter(
            entity -> {
              double dist = entity.distance(currentEnemy);
              return dist <= rangeOfVision;
            }).toList();
    if (heroList.isEmpty()) {
      return null;
    }

    double nearestHeroDist = Double.MAX_VALUE;
    Entity nearestHero = heroList.get(0);

    for (Entity hr : heroList) {
      double dist =
          sqrt(
              hr.getPosition().getX() * hr.getPosition().getX()
                  + hr.getPosition().getY() * hr.getPosition().getY());
      if (dist < nearestHeroDist) {
        nearestHero = hr;
        nearestHeroDist = dist;
      }
    }
    return nearestHero;
  }

  @CheckForNull
  private Entity findNearestWeaponCurrentType(WeaponType type) {
    List<Entity> weaponList = FXGL.getGameWorld().getEntitiesByType(EntityType.WEAPON);
    weaponList = weaponList.stream()
            .filter(
                    entity -> {
                      double dist = entity.distance(currentEnemy);
                      return dist <= rangeOfVision;
                    })
            .filter(weapon -> weapon.getComponent(WeaponComponent.class).getWeapon().getType().getWeaponType() == type).filter(w -> w.getComponent(
            DataComponent.class).getOwnerID() == -1).toList();
    if (weaponList.isEmpty()) {
      return null;
    }

    double nearestWeaponDist = Double.MAX_VALUE;
    Entity nearestWeapon = weaponList.get(0);

    for (Entity weapon : weaponList) {
      double dist =
              sqrt(
                      weapon.getPosition().getX() * weapon.getPosition().getX()
                              + weapon.getPosition().getY() * weapon.getPosition().getY());
      if (dist < nearestWeaponDist) {
        nearestWeapon = weapon;
        nearestWeaponDist = dist;
      }
    }
    return nearestWeapon;
  }

}
