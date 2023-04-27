package ru.nsu.fit.dib.projectdib.environment.levelLoader;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.level.Level;
import javafx.scene.effect.Effect;
import ru.nsu.fit.dib.projectdib.EntityType;

public class LevelSetter {

  public static Level setLevelFromMap(String mapFileName, GameWorld gameWorld) {
    LevelLoader levelLoader = new TMXLevelLoader();
    Level level = levelLoader.load(mapFileName, gameWorld);
    gameWorld.setLevel(level);
    return level;
  }
}