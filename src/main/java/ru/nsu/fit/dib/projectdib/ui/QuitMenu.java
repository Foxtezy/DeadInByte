package ru.nsu.fit.dib.projectdib.ui;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._fontDustyPro;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._menuButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._menuSelectedButton;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ru.nsu.fit.dib.projectdib.data.Musics;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.ui.UIElements.ImageButton;
import ru.nsu.fit.dib.projectdib.utils.BackgroundMusicController;
import ru.nsu.fit.dib.projectdib.utils.SoundsController;

public class QuitMenu extends AnchorPane {
  public ImageButton resume;
  public ImageButton quit;
  public ImageButton settings;
  public VBox box;
  public SettingsMenu settingsMenu;
  public QuitMenu() {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    Font font = Font.loadFont(classloader.getResourceAsStream(_fontDustyPro), 65);
    Image unpushed = new Image(_menuButton, 1020, 180, true, false);
    Image pushed = new Image(_menuSelectedButton, 1020, 180, true, false);
    resume = new ImageButton("resume",font,"#5ae8a8", "#2b2944",pushed,unpushed);
    quit = new ImageButton("quit",font,"#5ae8a8", "#2b2944",pushed,unpushed);
    settings = new ImageButton("settings",font,"#5ae8a8", "#2b2944",pushed,unpushed);
    box = new VBox();
    box.setAlignment(Pos.CENTER);
    box.getChildren().addAll(resume,settings,quit);
    AnchorPane.setBottomAnchor(box,20.0);
    AnchorPane.setLeftAnchor(box,20.0);
    AnchorPane.setRightAnchor(box,20.0);
    AnchorPane.setTopAnchor(box,20.0);
    settingsMenu = new SettingsMenu();
    settingsMenu.setVisible(false);
    settingsMenu.setDisable(true);
    AnchorPane.setBottomAnchor(settingsMenu,20.0);
    AnchorPane.setLeftAnchor(settingsMenu,20.0);
    AnchorPane.setRightAnchor(settingsMenu,20.0);
    AnchorPane.setTopAnchor(settingsMenu,20.0);
  }
  public void initialize(){
    this.setStyle("-fx-background-color: rgba(0,0,0,0.4);"
        + "-fx-padding: 20;");
    this.getChildren().add(box);
    settingsMenu.setPrefSize(this.getPrefWidth(),this.getPrefHeight());
    this.getChildren().add(settingsMenu);
    settingsMenu.setCloseEvent(()->{
      box.setVisible(true);
      box.setDisable(false);
    });
    resume.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      this.setDisable(true);
      this.setVisible(false);
    });
    quit.setOnMouseClicked(event ->{
      BackgroundMusicController.getBackgroundMusicControlleroller().setMusic(Musics.menu);
      SoundsController.getSoundsController().play(Sounds.select_button);
      FXGL.getGameController().gotoMainMenu();
    });
    settings.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      settingsMenu.setVisible(true);
      settingsMenu.setDisable(false);
      box.setVisible(false);
      box.setDisable(true);
    });
  }
}
