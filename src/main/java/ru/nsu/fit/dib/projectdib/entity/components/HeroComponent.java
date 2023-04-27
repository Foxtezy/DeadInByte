package ru.nsu.fit.dib.projectdib.entity.components;

import static com.almasb.fxgl.dsl.FXGL.newLocalTimer;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import ru.nsu.fit.dib.projectdib.EntityType;

import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.HPModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.JFXModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.DamageType;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.DamageModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.SquareDamageModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TimingsModule;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_height;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_numberColumns;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_width;

/**
 * Описывает движение игрока.
 */
public class HeroComponent extends CreatureComponent implements Moving {
  private LocalTimer shootTimer = newLocalTimer();

  public HeroComponent(Creature hero, Point2D localAnchor) {
    super(hero,localAnchor);
  }

  public void attack() {
    if (!shootTimer.elapsed(
        Duration.millis(creature.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(
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
      }
      /*
      FXGL.spawn("projectile", new SpawnData(getEntity().getPosition().getX() + localAnchor.getX(),
          getEntity().getPosition().getY() + localAnchor.getY())
          .put("typeProj", Projectiles.ARROW));
      */
    });
    shootTimer.capture();
  }

  private List<Entity> getEntityInAttackArea() {
    Weapon weapon = creature.getModule(CreatureWeaponModule.class).getActiveWeapon();
    double radius = weapon.getModule(SquareDamageModule.class).getRadius();
    double distance = weapon.getModule(SquareDamageModule.class).getDistance();
    Point2D vectorView = getDirectionView();
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
                && triangle.contains(entity.getPosition()) && entity!=getEntity()).toList());
    return list;
  }
  @Override
  public void left() {
    physics.setVelocityX(-creature.getSpeed());
  }

  @Override
  public void right() {
    physics.setVelocityX(creature.getSpeed());
  }

  @Override
  public void up() {
    //да, тут должен быть именно минус
    physics.setVelocityY(-creature.getSpeed());
  }

  @Override
  public void down() {
    //а тут плюс
    physics.setVelocityY(creature.getSpeed());
  }
  @Override
  public void stop() {
    physics.setVelocityY(0);
    physics.setVelocityX(0);
  }
}
