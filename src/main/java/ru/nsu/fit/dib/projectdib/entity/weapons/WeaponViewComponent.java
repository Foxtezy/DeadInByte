package ru.nsu.fit.dib.projectdib.entity.weapons;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.CreatureViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.EnemyViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HeroViewComponent;

public class WeaponViewComponent extends Component {
  double radius;
  double distance;
  Double imgRadius;
  Double angle;

  public Point2D getRotation() {
    return rotation;
  }

  private Point2D rotation;

  public WeaponViewComponent(Double imgRadius, Double angle, double radius, double distance){
    this.imgRadius=imgRadius;
    this.angle=angle;
    this.radius=radius;
    this.distance=distance;
  }
  @Override
  public void onAdded() {
    getEntity().setScaleY(0.75);
    getEntity().setScaleX(0.75);
    getEntity().setScaleY(0.75);
    getEntity().setRotation(90);
    //imgRadius = weapon.getModule(TextureModule.class).getImageRadius();
    //angle = weapon.getModule(TextureModule.class).getAngle();
    //rotatedPoint = getEntity().getPosition().add(new Point2D(0, 20));
    //radius = weapon.getModule(SquareDamageModule.class).getRadius();
    //distance = weapon.getModule(SquareDamageModule.class).getDistance();
  }

  @Override
  public void onUpdate(double tpf) {
    Entity user = getEntity().getComponent(WeaponComponent.class).getUser();
    if (user != null) {
      Point2D userPos = user.getPosition();
      Point2D vectorView=null;
      if (user.hasComponent(HeroViewComponent.class)) {
        vectorView = user.getComponent(HeroViewComponent.class).getDirectionView();
      }
      else if (user.hasComponent(EnemyViewComponent.class)) {
        vectorView = user.getComponent(EnemyViewComponent.class).getDirectionView();
      }
      if (vectorView==null) return;
      rotation = FXGLMath.rotate(vectorView.normalize(), new Point2D(0, 0), angle);
      Point2D position = userPos.add(vectorView.normalize().multiply(imgRadius));
      getEntity().rotateToVector(rotation);

      getEntity().setX(position.getX());
      getEntity().setY(position.getY());
    }
  }
}
