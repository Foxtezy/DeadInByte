package ru.nsu.fit.dib.projectdib.entity.components.enemyAI;

import static com.almasb.fxgl.core.math.FXGLMath.sqrt;
import static com.almasb.fxgl.dsl.FXGL.geto;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.astar.AStarPathfinder;
import com.google.javascript.jscomp.jarjar.javax.annotation.CheckForNull;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.PlayerChaseComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponViewComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class EnemyAiComponent extends Component {
  LazyValue<AStarPathfinder> pathfinder = new LazyValue<>(() -> new AStarPathfinder(geto("grid")));
  //AStarMoveComponent aStar = new AStarMoveComponent(new LazyValue<>(() -> geto("grid")));
  //AStarPathfinder path = new AStarPathfinder(geto("grid"));
  AStar aStar = new AStar(new LazyValue<>(() -> geto("grid")));
  private Map<Integer, Entity> gameMapOfEntities = MCClient.getClientState().getIdHashTable();
  private Entity currentEnemy;
  private List<Entity> heroList;
  private List<Point2D> memoryOfLastHeroesPositions;

  PlayerChaseComponent chase;

  @Override
  public void onAdded() {
    currentEnemy = getEntity();
    currentEnemy.addComponent(aStar);
    //currentEnemy.addComponent(aStar);
    //chase = new PlayerChaseComponent(aStar);
  }

  @Override
  public void onUpdate(double tpf) {
    Entity target = findNearestHero();
    if (target == null) {
      throw new NullPointerException("no hero in EnemyAiComponent in onUpdate()");
    }
    int x = target.call("getCellX");
    int y = target.call("getCellX");
    currentEnemy.getComponent(AStar.class).moveToCell(x, y);
    /*List<AStarCell> cells = path.findPath((int) currentEnemy.getPosition().getX(),
        (int) currentEnemy.getPosition().getY(),
        (int) target.getPosition().getX(), (int) target.getPosition().getY());*/
    /*List<AStarCell> path = pathfinder.get().findPath((int) currentEnemy.getAnchoredPosition().getX() / 16,
        (int) currentEnemy.getAnchoredPosition().getY() / 16,
        target.call("getCellX"), target.call("getCellY"));*/
    //int x = path.get(1).getX();
    //int y = path.get(1).getY();
    //Point2D position = new Point2D(x, y);
    //currentEnemy.getComponent(ServerControlComponent.class).moveToPoint(position);
    //currentEnemy.getComponent(AStarMoveComponent.class).moveToCell(x, y);
    // chase.move(target);
//    chase.move(target);
//    currentEnemy.getComponent(PlayerChaseComponent.class).move(target);

//    int hp = 15; // TODO entity.getComponent(HealthDoubleComponent.class). ... .getHP();
//    int maxHp = 200;
//
//    if (hp <= maxHp * 0.15) {
//      // TODO RUNNING AWAY
//      return;
//    }
//    Entity target = findNearestHero();
//    if (target == null) {
//      return;
//    } else {
//      // move(target.getPosition())
//      // attack();
//    }
//
//    // Если нет оружия, тогда ищет упавшее оружие в зоне видимости и подбирает
//    if (!entity.getComponent(WeaponInventoryComponent.class).hasWeapon()) {
//      Entity droppedWeapon = findNearestDroppedWeapon();
//      // TODO SEARCHING DROPPED WEAPON
//      // move(dropped.Weapon.getPosition);
//      // take();
//    }
  }

  @CheckForNull
  private Entity findNearestHero() {
    return FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
    /*List<Entity> heroList =
        gameMapOfEntities.values().stream()
            .filter(entities -> entities.hasComponent(HeroViewComponent.class))
          //  .filter(heroes -> distanceBetweenEntities(currentEnemy, heroes) < 1555500)
            .toList();
    System.out.println();
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
    return nearestHero;*/
  }

  @CheckForNull
  private Entity findNearestDroppedWeapon() {
    List<Entity> droppedWp =
        gameMapOfEntities.values().stream()
            .filter(x -> x.hasComponent(WeaponViewComponent.class))
            .filter(weapon -> distanceBetweenEntities(currentEnemy, weapon) < 500)
            .toList();
    if (droppedWp.isEmpty()) {
      return null;
    }
    double nearestWpDist = Double.MAX_VALUE;
    Entity nearestWp = droppedWp.get(0);
    for (Entity ent : droppedWp) {
      double dist =
          sqrt(
              ent.getPosition().getX() * ent.getPosition().getX()
                  + ent.getPosition().getY() * ent.getPosition().getY());
      if (dist < nearestWpDist) {
        nearestWp = ent;
        nearestWpDist = dist;
      }
    }
    return nearestWp;
  }

  private void takeNearestDroppedWeapon() {
    Entity nearestWp = findNearestDroppedWeapon();
    if (nearestWp == null) {
      // running away
    }
    // move to nearestWp.getPosition;
    // collidable ? take : move()
  }

  private double distanceBetweenEntities(Entity a, Entity b) {
    double Xa = a.getPosition().getX();
    double Ya = a.getPosition().getY();
    double Xb = b.getPosition().getX();
    double Yb = b.getPosition().getY();
    return sqrt((Xa - Xb) * (Xa - Xb) + (Ya - Yb) * (Ya - Yb));
  }

  // TODO  No lasers?
  //    private List<Entity> heroesInFieldOfView(List<Entity> heroesInRangeOfVisibility){
  //    }
}
