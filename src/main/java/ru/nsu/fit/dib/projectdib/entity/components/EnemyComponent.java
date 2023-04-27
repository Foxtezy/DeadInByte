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

public class EnemyComponent extends CreatureComponent {

  public EnemyComponent(Creature creature, Point2D localAnchor) {
    super(creature, localAnchor);
  }
}