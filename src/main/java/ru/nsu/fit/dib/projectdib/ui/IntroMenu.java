package ru.nsu.fit.dib.projectdib.ui;

import com.almasb.fxgl.app.scene.IntroScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Сцена загрузки уровня.
 */
public class IntroMenu extends IntroScene {

  @Override
  public void startIntro() {
    Pane canvas = getContentRoot();
    canvas.setStyle("-fx-background-color: #121218;");
    BorderPane main = new BorderPane();

  }
}
