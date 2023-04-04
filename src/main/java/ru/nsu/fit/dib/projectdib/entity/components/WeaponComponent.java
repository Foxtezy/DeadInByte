package ru.nsu.fit.dib.projectdib.entity.components;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;
import static java.lang.Math.abs;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.JFXModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.SquareDamageModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;

public class WeaponComponent extends Component {

  private final Weapon weapon;
  double radius;
  double distance;
  Double imgRadius;
  Double angle;
  Point2D rotatedPoint;
  Point2D lastVectorView = new Point2D(0, 0);

  public WeaponComponent(Weapon weapon) {
    this.weapon = weapon;
  }

  @Override
  public void onAdded() {
    super.onAdded();
    getEntity().setScaleX(0.75);
    getEntity().setScaleY(0.75);
    getEntity().setRotation(90);
    imgRadius = weapon.getModule(TextureModule.class).getImageRadius();
    angle = weapon.getModule(TextureModule.class).getAngle();
    rotatedPoint = getEntity().getPosition().add(new Point2D(0, 20));
    radius = weapon.getModule(SquareDamageModule.class).getRadius();
    distance = weapon.getModule(SquareDamageModule.class).getDistance();
  }

  public Weapon getWeapon() {
    return weapon;
  }

  @Override
  public void onUpdate(double tpf) {
    if (weapon.getUser() != null) {
      Point2D userPos = weapon.getUser().getModule(JFXModule.class).getComponent().getEntity().getPosition();
      Point2D vectorView = null;
      Component userComponent = weapon.getUser().getModule(JFXModule.class).getComponent();
      if(weapon.getUser().getModule(JFXModule.class).hasHeroComponent()){
        vectorView = ((HeroComponent)userComponent).getDirectionView();
      }else{
        //TODO correct enemies duration
        vectorView = FXGL.getInput().getMousePositionWorld();
      }
      if(vectorView == null){
        throw new NullPointerException("vectorView == null in WeaponComponent.onUpdate()");
      }
      Point2D rotation = FXGLMath.rotate(vectorView.normalize(), new Point2D(0, 0), angle);
      Point2D position = userPos.add(vectorView.normalize().multiply(imgRadius));
      getEntity().rotateToVector(rotation);

      getEntity().setX(position.getX());
      getEntity().setY(position.getY());
    }
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
