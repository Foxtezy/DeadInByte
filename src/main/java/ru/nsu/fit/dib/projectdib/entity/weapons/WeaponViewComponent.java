package ru.nsu.fit.dib.projectdib.entity.weapons;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.CreatureViewComponent;

public class WeaponViewComponent extends Component {
  double radius;
  double distance;
  Double imgRadius;
  Double angle;

  public Point2D getRotation() {
    return rotation;
  }

  private Point2D rotation;

  WeaponViewComponent(Double imgRadius, Double angle, double radius,double distance){
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
    if (getEntity().getComponent(WeaponComponent.class).getUser() != null && getEntity().getComponent(WeaponComponent.class).getUser()!=null) {
      Point2D userPos = getEntity().getComponent(WeaponComponent.class).getUser().getPosition();
      Point2D vectorView = getEntity().getComponent(WeaponComponent.class).getUser().getComponent(CreatureViewComponent.class).getDirectionView();
      rotation = FXGLMath.rotate(vectorView.normalize(), new Point2D(0, 0), angle);
      Point2D position = userPos.add(vectorView.normalize().multiply(imgRadius));
      getEntity().rotateToVector(rotation);

      getEntity().setX(position.getX());
      getEntity().setY(position.getY());
    }
  }
}
