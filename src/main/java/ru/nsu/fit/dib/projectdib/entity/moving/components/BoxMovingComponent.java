package ru.nsu.fit.dib.projectdib.entity.moving.components;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class BoxMovingComponent extends Component  {
        private PhysicsComponent physics;
        @Override
        public void onUpdate(double tpf) {
                physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(100000, (-1) * tpf)));
        }
}
