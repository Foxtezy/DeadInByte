package ru.nsu.fit.dib.projectdib.ui;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.ui.UIController;

/**
 * Пока не знаю зачем, но пусть будет. По идее нужен для обработки событий кнопок. Но что то пошло
 * не так...
 */
public class GameUIController implements UIController {

  private GameScene gameScene;

  public GameUIController(GameScene gameScene) {
    this.gameScene = gameScene;
  }

  @Override
  public void init() {

  }

  private void startGame() {

  }
}
