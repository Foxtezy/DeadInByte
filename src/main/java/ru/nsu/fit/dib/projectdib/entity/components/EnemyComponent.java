package ru.nsu.fit.dib.projectdib.entity.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.JFXModule;

import java.util.function.Function;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.*;

public class EnemyComponent extends Component {
    //View
    private final double scale = 0.07;
    private final AnimatedTexture texture;
    protected Creature creature;
    AnimationChannel animationMovement;
    AnimationChannel animationStanding;
    Point2D localAnchor;
    boolean stateChanged = true;
    Function<Entity, Point2D> directionView;
    //Physic
    private PhysicsComponent physics;
    private String currentWeapon;
    private boolean isMoving = false;

    public EnemyComponent(Creature creature, Point2D localAnchor) {
        this.localAnchor = localAnchor;
        this.creature = creature;
        int creatureNumber = creature.getModule(JFXModule.class).getImageID();

        Image img = new Image(_enemy);
        this.animationMovement = new AnimationChannel(img,
                _enemy_numberColumns, _enemy_width, _enemy_height, Duration.millis(600),
                3 + creatureNumber * _enemy_numberColumns, (creatureNumber + 1) * _enemy_numberColumns);


        this.animationStanding = new AnimationChannel(img,
                _enemy_numberColumns, _enemy_width, _enemy_height, Duration.millis(600),
                1 + creatureNumber * _enemy_numberColumns, 3 + creatureNumber * _enemy_numberColumns);

        texture = new AnimatedTexture(animationStanding);
        texture.loop();
        texture.setPreserveRatio(true);
    }

    public void bindDirectionView(Function<Entity, Point2D> directionView) {
        this.directionView = directionView;
    }

    public Point2D getDirectionView() {
        return directionView.apply(getEntity());
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(15, 20));
        entity.getViewComponent().addChild(texture);
        entity.setScaleUniform(scale);
    }

    @Override
    public void onUpdate(double tpf) {
        if (isMoving != isMoving()) {
            stateChanged = true;
            isMoving = isMoving();
        }
        //Изменилось ли состояние
        if (stateChanged) {
            //texture.loopAnimationChannel(isMoving() ? animationMovement : animationStanding);
            AnimationChannel animation = texture.getAnimationChannel();
            //Движется или нет
            if (isMoving()) {
                texture.loopAnimationChannel(animationMovement);
            } else {
                texture.loopAnimationChannel(animationStanding);
            }
            stateChanged = false;
        }

        Point2D mouseVelocity = getDirectionView();
        //Поворот
        if (mouseVelocity.angle(1, 0) <= 90) {
            rotate(CreatureComponent.SIDE.RIGHT);
        } else {
            rotate(CreatureComponent.SIDE.LEFT);
        }
        physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
    }

    public Creature getCreature() {
        return creature;
    }

    private boolean isMoving() {
        return FXGLMath.abs(physics.getVelocityX()) > 0 || FXGLMath.abs(physics.getVelocityY()) > 0;
    }

    public void rotate(CreatureComponent.SIDE side) {
        if (side == CreatureComponent.SIDE.RIGHT) {
            texture.setScaleX(1);
        } else {
            texture.setScaleX(-1);
        }
    }
    enum SIDE {
        LEFT,
        RIGHT
    }
}