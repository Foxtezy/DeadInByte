package ru.nsu.fit.dib.projectdib.UI;

import static com.almasb.fxgl.dsl.FXGLForKtKt.animationBuilder;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.ui.UIController;

public class GameUIController implements UIController {

  private GameScene gameScene;

  public GameUIController(GameScene gameScene) {
    this.gameScene=gameScene;
  }

  @Override
  public void init() {

  }

  private void startGame() {

  }
}
