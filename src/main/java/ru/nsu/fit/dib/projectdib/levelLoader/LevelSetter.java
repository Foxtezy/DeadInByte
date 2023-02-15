package ru.nsu.fit.dib.projectdib.levelLoader;

import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.level.Level;

public class LevelSetter {

  public static Level setLevelFromMap(String mapFileName, GameWorld gameWorld) {
    LevelLoader levelLoader = new TMXLevelLoader();
    Level level = levelLoader.load(mapFileName, gameWorld);
    gameWorld.setLevel(level);
    return level;
  }
}
