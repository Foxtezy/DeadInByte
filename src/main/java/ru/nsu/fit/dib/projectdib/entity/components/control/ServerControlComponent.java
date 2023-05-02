package ru.nsu.fit.dib.projectdib.entity.components.control;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;

public class ServerControlComponent extends Component {

  private static final double ERROR_RATE = 4;
  private Point2D newPosition;
  private PhysicsComponent physics;

  public ServerControlComponent(){

  }
  public void moveToPoint(Point2D position){
    newPosition=position;
    move(position);
  }
  private void move(Point2D position){
    Point2D movingVector = position.add(entity.getPosition().multiply(-1));
    getEntity().getComponent(PhysicsComponent.class).setLinearVelocity(movingVector.normalize().multiply(getSpeed()));
  }
  @Override
  public void onAdded(){
    physics = getEntity().getComponent(PhysicsComponent.class);
    newPosition = getEntity().getPosition();
  }
  @Override
  public void onUpdate(double tpf) {
    if (newPosition.add(getEntity().getPosition().multiply(-1)).magnitude()<ERROR_RATE)
    {
      physics.setLinearVelocity(0,0);
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }
  private double getSpeed(){
    return getEntity().getComponent(CreatureComponent.class).getCreature().getSpeed();
  }
}
