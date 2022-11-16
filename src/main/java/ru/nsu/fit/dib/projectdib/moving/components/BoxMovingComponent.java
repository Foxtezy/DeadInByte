package ru.nsu.fit.dib.projectdib.moving.components;

import static com.almasb.fxgl.dsl.FXGL.image;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.moving.MovingInterface;

public class BoxMovingComponent extends Component  {

        private PhysicsComponent physics;

        @Override
        public void onUpdate(double tpf) {
                physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(100000, (-1) * tpf)));
        }


}
