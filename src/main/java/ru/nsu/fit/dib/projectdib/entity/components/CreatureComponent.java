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
import ru.nsu.fit.dib.projectdib.entity.components.CreatureViewComponent.Side;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.CreatureWeaponModule;
import ru.nsu.fit.dib.projectdib.entity.creatures.modules.JFXModule;
import ru.nsu.fit.dib.projectdib.entity.weapons.Weapon;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules.TextureModule;

public class CreatureComponent extends Component {

  //========View========
  private final double scale = 0.07;
  protected Creature creature;
  Point2D localAnchor;
  Function<Entity, Point2D> directionView;
  private CreatureViewComponent viewComponent;

  //========Physics========
  protected PhysicsComponent physics;
  private String currentWeapon;

  public CreatureComponent(Creature creature, Point2D localAnchor) {
    this.localAnchor = localAnchor;
    this.creature = creature;
    //animation settings
  }

  public void bindDirectionView(Function<Entity, Point2D> directionView) {
    this.directionView = directionView;
  }

  @Override
  public void onAdded() {
    if (getEntity().hasComponent(HeroViewComponent.class)) {
      viewComponent = getEntity().getComponent(HeroViewComponent.class);
    }
    else viewComponent = getEntity().getComponent(EnemyViewComponent.class);
    entity.getTransformComponent().setScaleOrigin(new Point2D(15, 20));
    entity.setScaleUniform(scale);
  }

  @Override
  public void onUpdate(double tpf) {
    Point2D mouseVelocity = getDirectionView();
    //Поворот
    if (mouseVelocity.angle(1, 0) <= 90) {
      viewComponent.rotate(Side.RIGHT);
    } else {
      viewComponent.rotate(Side.LEFT);
    }
    physics.setLinearVelocity(physics.getLinearVelocity().multiply(Math.pow(1000, (-1) * tpf)));
  }

  public Creature getCreature() {
    return creature;
  }

  public void attack() {
  }

  public Point2D getDirectionView() {
    return directionView.apply(getEntity());
  }

  //Actions
  public List<Entity> findWeapon() {
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
    creature.getModule(CreatureWeaponModule.class)
        .changeWeapon(WeaponFactory.getWeapon(Weapons.Hand));
  }

  public void swapWeapon() {
    if (!creature.getModule(CreatureWeaponModule.class).getActiveWeapon().getName()
        .equals("hand")) {
      creature.getModule(CreatureWeaponModule.class).getActiveWeapon()
          .getModule(TextureModule.class).getComponent().getEntity().setVisible(false);
    }
    Weapon weapon = creature.getModule(CreatureWeaponModule.class).getNextWeapon();
    if (!weapon.getName().equals("hand")) {
      weapon.getModule(TextureModule.class).getComponent().getEntity().setVisible(true);
    }
  }
}
