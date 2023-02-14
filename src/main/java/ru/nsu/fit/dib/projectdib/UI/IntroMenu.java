package ru.nsu.fit.dib.projectdib.UI;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.IntroScene;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class IntroMenu extends IntroScene {
  @Override
  public void startIntro() {
    Pane canvas = getContentRoot();
    canvas.setStyle("-fx-background-color: #121218;");
    BorderPane main = new BorderPane();

  }
}
