package ru.nsu.fit.dib.projectdib.entity.components.control;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;

public class PlayerControlComponent extends Component implements Moving {
  private PhysicsComponent physics;
  public PlayerControlComponent(){
  }
  @Override
  public void onAdded(){
    physics = getEntity().getComponent(PhysicsComponent.class);
  }
  @Override
  public void left() {
    physics.setVelocityX(-getSpeed());
  }

  @Override
  public void right() {
    physics.setVelocityX(getSpeed());
  }

  @Override
  public void up() {
    //да, тут должен быть именно минус
    physics.setVelocityY(-getSpeed());
  }

  @Override
  public void down() {
    //а тут плюс
    physics.setVelocityY(getSpeed());
  }
  @Override
  public void stop() {
    physics.setVelocityY(0);
    physics.setVelocityX(0);
  }
  @Override
  public void onUpdate(double tpf) {
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }
  private double getSpeed(){
    return getEntity().getComponent(CreatureComponent.class).getCreature().getSpeed();
  }
}
