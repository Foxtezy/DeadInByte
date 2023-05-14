package ru.nsu.fit.dib.projectdib.ui;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.heroes_image;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.UIController;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HPViewComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.ui.UIElements.ImageButton;

/**
 * Пока не знаю зачем, но пусть будет. По идее нужен для обработки событий кнопок. Но что то пошло
 * не так...
 */
public final class GameUIController implements UIController {
  @FXML
  public AnchorPane mainPane;
  @FXML
  public VBox healthBarBox;
  @FXML
  public HBox buttonsBox;
  @FXML
  private final GameScene gameScene;
  ImageButton menu;
  ImageButton character;

  public GameUIController(GameScene gameScene) {
    this.gameScene = gameScene;
  }

  @Override
  public void init() {
    mainPane.setMinHeight(gameScene.getViewport().getHeight());
    mainPane.setMinWidth(gameScene.getViewport().getWidth());
    addHPBars();
    addButtons();
  }

  private void addButtons() {
    menu = new ImageButton("menu",new Image("assets/ui/elements/menu_selected.png"),
        new Image("assets/ui/elements/menu.png"));
    menu.setOnMouseClicked(event->{
    });
    character = new ImageButton("character",new Image("assets/ui/elements/character_selected.png"),new Image("assets/ui/elements/character.png"));
    buttonsBox.getChildren().add(character);
    buttonsBox.getChildren().add(menu);
  }

  private synchronized void addHPBars() {
    healthBarBox.getChildren().clear();
    List<Entity> entities = new java.util.ArrayList<>(queue.stream().toList());
    Entity client = entities.stream().filter(e->e.getComponent(DataComponent.class).getId()==MCClient.getClientId()).findFirst().get();
    entities.remove(client);
    healthBarBox.getChildren().add(client.getComponent(HPViewComponent.class).getHPBar());
    entities.forEach(entity -> healthBarBox.getChildren().add(entity.getComponent(HPViewComponent.class).getHPBar()));
  }

  public void addHPBar(Entity entity) {
    healthBarBox.getChildren().add(entity.getComponent(HPViewComponent.class).getHPBar());
  }
  public volatile static BlockingQueue<Entity> queue = new LinkedBlockingQueue<>();
  public void removeHPBar(Entity entity){
    healthBarBox.getChildren().add(entity.getComponent(HPViewComponent.class).getHPBar());
  }
}
