package ru.nsu.fit.dib.projectdib.entity.components;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_height;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_numberColumns;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_width;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.JFXModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;

public class CreatureComponent extends Component implements Moving {

  //========View========
  private final double scale = 0.07;
  private final AnimatedTexture texture;
  protected Creature creature;
  AnimationChannel animationMovement;
  AnimationChannel animationStanding;
  Point2D localAnchor;
  boolean stateChanged = true;
  Function<Entity, Point2D> directionView;

  //========Physics========
  private PhysicsComponent physics;
  private String currentWeapon;
  private boolean isMoving = false;
  private static final Image img = new Image(_player);

  public CreatureComponent(Creature creature, Point2D localAnchor) {
    this.localAnchor = localAnchor;
    this.creature = creature;
    int creatureNumber = creature.getModule(JFXModule.class).getImageID();
    //animation settings
    animationMovement = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(600),
        5 + creatureNumber * 13, 8 + creatureNumber * 13);
    animationStanding = new AnimationChannel(img,
        _player_numberColumns, _player_width, _player_height, Duration.millis(300),
        creatureNumber * 13,
        1 + creatureNumber * 13);
    texture = new AnimatedTexture(animationStanding);
    texture.loop();
    texture.setPreserveRatio(true);
  }

  public void bindDirectionView(Function<Entity, Point2D> directionView) {
    this.directionView = directionView;
  }

  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(new Point2D(15, 20));
    entity.getViewComponent().addChild(texture);
    entity.setScaleUniform(scale);
    //setCurrentWeapon("bow");
  }

  @Override
  public void onUpdate(double tpf) {
    if (isMoving && !isMoving() || !isMoving && isMoving()) {
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
      rotate(SIDE.RIGHT);
    } else {
      rotate(SIDE.LEFT);
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }

  public Creature getCreature() {
    return creature;
  }

  public void attack() {
  }

  //======================================[Moving]==================================================
  @Override
  public void left() {
    physics.setVelocityX(-creature.getSpeed());
  }

  @Override
  public void right() {
    physics.setVelocityX(creature.getSpeed());
  }

  @Override
  public void up() {
    //да, тут должен быть именно минус
    physics.setVelocityY(-creature.getSpeed());
  }

  @Override
  public void down() {
    //а тут плюс
    physics.setVelocityY(creature.getSpeed());
  }

  @Override
  public void stop() {
    physics.setVelocityY(0);
    physics.setVelocityX(0);
  }
  public void moveByVector(Point2D vector){
    physics.setVelocityX(vector.getX()*creature.getSpeed());
    physics.setVelocityY(vector.getY()*creature.getSpeed());
  }

  //==================================================================================================
  public Point2D getDirectionView() {
    return directionView.apply(getEntity());
  }

  private boolean isMoving() {
    return FXGLMath.abs(physics.getVelocityX()) > 0 || FXGLMath.abs(physics.getVelocityY()) > 0;
  }

  public void rotate(SIDE side) {
    if (side == SIDE.RIGHT) {
      texture.setScaleX(1);
    } else {
      texture.setScaleX(-1);
    }
  }
  enum SIDE {
    LEFT,
    RIGHT
  }
  //Actions
  public List<Entity> findWeapon()
  {
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
    creature.getModule(CreatureWeaponModule.class).changeWeapon(WeaponFactory.getWeapon(Weapons.Hand));
  }

  public void swapWeapon() {
    if (!creature.getModule(CreatureWeaponModule.class).getActiveWeapon().getName().equals("hand")) {
      creature.getModule(CreatureWeaponModule.class).getActiveWeapon().getModule(TextureModule.class).getComponent().getEntity().setVisible(false);
    }
    Weapon weapon = creature.getModule(CreatureWeaponModule.class).getNextWeapon();
    if (!weapon.getName().equals("hand")) {
      weapon.getModule(TextureModule.class).getComponent().getEntity().setVisible(true);
    }
  }
}
