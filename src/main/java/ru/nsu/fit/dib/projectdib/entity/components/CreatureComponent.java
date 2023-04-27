package ru.nsu.fit.dib.projectdib.entity.components;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.CreatureViewComponent.Side;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;

public class CreatureComponent extends Component {

  //========View========
  private final double scale = 0.07;
  protected Creature creature;
  private Function<Entity, Point2D> directionView;
  private CreatureViewComponent viewComponent;
  private Point2D newPosition;

  //========Physics========
  protected PhysicsComponent physics;
  private String currentWeapon;

  public CreatureComponent(Creature creature, Point2D localAnchor) {
    this.creature = creature;
    //animation settings
  }

  public void bindDirectionView(Function<Entity, Point2D> directionView) {
    this.directionView = directionView;
  }

  @Override
  public void onAdded() {
    if (getEntity().hasComponent(HeroViewComponent.class)) {
      viewComponent = getEntity().getComponent(HeroViewComponent.class);
    }
    else viewComponent = getEntity().getComponent(EnemyViewComponent.class);
    entity.getTransformComponent().setScaleOrigin(new Point2D(15, 20));
    entity.setScaleUniform(scale);
  }

  @Override
  public void onUpdate(double tpf) {
    Point2D mouseVelocity = getDirectionView();
    //Поворот
    if (mouseVelocity.angle(1, 0) <= 90) {
      viewComponent.rotate(Side.RIGHT);
    } else {
      viewComponent.rotate(Side.LEFT);
    }
    if (newPosition!=null) {
      double l=newPosition.add(getEntity().getPosition().multiply(-1)).magnitude();
      if (l<4)
      {
        physics.setLinearVelocity(0,0);
      }
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }

  public Creature getCreature() {
    return creature;
  }

  public void moveToPoint(Point2D position){
    newPosition=position;
    move(position);
  }
  private void move(Point2D position){
    Point2D movingVector = position.add(entity.getPosition().multiply(-1));
    physics.setLinearVelocity(movingVector.normalize().multiply(getCreature().getSpeed()));
  }
  public void attack() {
  }
  public Point2D getDirectionView() {
    return directionView.apply(getEntity());
  }

  //Actions
  public List<Entity> findWeapon() {
    Creature hero = getCreature();
    List<Entity> list = new ArrayList<>(
        getGameWorld().getEntitiesByType(EntityType.WEAPON).stream()
            .filter(weapon -> weapon.hasComponent(CollidableComponent.class)
                && weapon.isColliding(getEntity())).toList());
    //Удаляем все оружие игрока из списка
    hero.getModule(CreatureWeaponModule.class).getWeaponsList().forEach(weapon -> {
      if (!Objects.equals(weapon.getName(), "hand")) {
        list.remove(weapon.getModule(TextureModule.class).getComponent().getEntity());
      }
    });
    return list;
  }

  public void takeWeapon(Entity weaponEntity) {
    Weapon weapon = weaponEntity.getComponent(WeaponComponent.class).getWeapon();
    creature.getModule(CreatureWeaponModule.class).changeWeapon(weapon);
  }

  public void throwWeapon() {
    creature.getModule(CreatureWeaponModule.class)
        .changeWeapon(WeaponFactory.getWeapon(Weapons.Hand));
  }

  public void swapWeapon() {
    if (!creature.getModule(CreatureWeaponModule.class).getActiveWeapon().getName()
        .equals("hand")) {
      creature.getModule(CreatureWeaponModule.class).getActiveWeapon()
          .getModule(TextureModule.class).getComponent().getEntity().setVisible(false);
    }
    Weapon weapon = creature.getModule(CreatureWeaponModule.class).getNextWeapon();
    if (!weapon.getName().equals("hand")) {
      weapon.getModule(TextureModule.class).getComponent().getEntity().setVisible(true);
    }
  }
}
