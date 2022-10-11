package ru.nsu.fit.dib.projectdib;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.entity.level.Level;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
public class App extends GameApplication {
  private Entity player;

  public static void main(String[] args) // запуск
      {
    launch(args);
  }

  @Override
  protected void initSettings(GameSettings settings) {
    //settings.setConfigClass(MyGameConfig.class)
    settings.setTitle("DiB");
  }

  // Управление
  @Override
  protected void initInput() {
    onKey(KeyCode.W, () -> player.translateY(-2));
    onKey(KeyCode.S, () -> player.translateY(2));
    onKey(KeyCode.A, () -> player.translateX(-2));
    onKey(KeyCode.D, () -> player.translateX(2));
  }

  // Спавн существ
  @Override
  protected void initGame() {
    getGameWorld().addEntityFactory(new Factory());
    //FXGL.setLevelFromMap("tmx/exlevel.tmx");
    Level level = FXGL.setLevelFromMap("tmx/exlevel.tmx");
    Viewport viewport = getGameScene().getViewport();
    this.player = spawn("player", getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);
  }
  public enum EntityType {
    PLAYER,
    PLATFORM
  }
}
