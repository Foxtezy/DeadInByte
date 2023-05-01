package ru.nsu.fit.dib.projectdib.entity.components;

import static java.lang.Math.abs;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import ru.nsu.fit.dib.projectdib.entity.components.fight.WeaponInventoryComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;

public class WeaponComponent extends Component {
  private Weapon weapon;
  private Entity user;

  public WeaponComponent(Weapon weapon) {
    this.weapon = weapon;
  }
  public Weapon getWeapon() {
    return weapon;
  }

  public boolean isActive() {
    if (user==null) return false;
    return (user.getComponent(WeaponInventoryComponent.class).getActiveWeapon()==getEntity());
  }

  public void setUser(Entity o) {
    user = o;
  }

  public Entity getUser() {
    return user;
  }
  /*
  public void attack() {

    List<Entity> list = getEntityInAttackArea();
    list.forEach(entity -> {
      entity.getComponent(PlayerComponent.class).attack();
      weapon.getUser().
    });

  }*/
/*
  private List<Entity> getEntityInAttackArea() {
    Point2D vectorView = weapon.getUser().getComponent().getVectorView();
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
                && triangle.contains(entity.getPosition())).toList());
    return list;
  }*/
}
