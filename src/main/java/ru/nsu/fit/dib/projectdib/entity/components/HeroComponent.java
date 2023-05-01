package ru.nsu.fit.dib.projectdib.entity.components;

import static com.almasb.fxgl.dsl.FXGL.newLocalTimer;

import com.almasb.fxgl.entity.Entity;

import java.util.List;
import javafx.geometry.Point2D;

import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;

/**
 * Описывает движение игрока.
 *
 */
@Deprecated
public class HeroComponent extends CreatureComponent{

  public HeroComponent(Creature hero, Point2D localAnchor) {
    super(new Creature(null));
    //super(hero,localAnchor);
  }

  public void attack() {
/*    if (!shootTimer.elapsed(
        Duration.millis(creature.getActiveWeapon().getModule(
            TimingsModule.class).getIterationTime()))) {
      return;
    }
    int attack = creature.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(DamageModule.class).getAttack();
    int damage = creature.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(DamageModule.class).getDamage();
    DamageType damageType = creature.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(DamageModule.class).getDamageType();
    List<Entity> list = getEntityInAttackArea();
    list.forEach(entity -> {
      int k = entity.getComponent(HeroComponent.class).creature.getArmorCoefficient() - attack;
      if (k>0){
        entity.getComponent(HeroComponent.class).creature.getModule(HPModule.class).changeHP(damage);
        System.out.println(entity.getComponent(HeroComponent.class).creature.getModule(HPModule.class).getCurrentHP()+" / "+ entity.getComponent(HeroComponent.class).creature.getModule(HPModule.class).getMaxHP());
      }*/
      /*
      FXGL.spawn("projectile", new SpawnData(getEntity().getPosition().getX() + localAnchor.getX(),
          getEntity().getPosition().getY() + localAnchor.getY())
          .put("typeProj", Projectiles.ARROW));
      */
/*    });
    shootTimer.capture();*/
  }

  private List<Entity> getEntityInAttackArea() {
/*    Weapon weapon = creature.getModule(CreatureWeaponModule.class).getActiveWeapon();
    double radius = weapon.getModule(SquareDamageModule.class).getRadius();
    double distance = weapon.getModule(SquareDamageModule.class).getDistance();
    Point2D vectorView = getEntity().getComponent(CreatureViewComponent.class).getDirectionView();
    Circle circle = new Circle(getEntity().getX(), getEntity().getY(), distance);
    Point2D r = FXGLMath.rotate(vectorView, new Point2D(0, 0), radius / 2);
    Point2D l = FXGLMath.rotate(vectorView, new Point2D(0, 0), radius / 2 + radius);
    Polygon triangle = new Polygon(
        getEntity().getX(), getEntity().getX(),
        getEntity().getX() + r.getX(), getEntity().getY() + r.getY(),
        getEntity().getX() + l.getX(), getEntity().getY() + l.getY());
    //Line rLine = new Line(getEntity().getX(),getEntity().getY(),
    //    getEntity().getX()+r.getEndX(),getEntity().getY()+r.getEndY());
    List<Entity> list = new ArrayList<>(
        getGameWorld().getEntitiesByType(EntityType.ENEMY).stream()
            .filter(entity -> entity.hasComponent(CollidableComponent.class)
                && circle.contains(entity.getPosition())
                && triangle.contains(entity.getPosition()) && entity!=getEntity()).toList());*/
    return null;//list;
  }
}
