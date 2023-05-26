package ru.nsu.fit.dib.projectdib.ui;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.UIController;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.dib.projectdib.App;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HPViewComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.ui.UIElements.ImageButton;
import ru.nsu.fit.dib.projectdib.utils.SoundsController;

/**
 * Пока не знаю зачем, но пусть будет. По идее нужен для обработки событий кнопок. Но что-то пошло
 * не так...
 */
public final class GameUIController implements UIController {
  private static GameUIController controller;
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
  static CharacterMenu characterMenu;
  QuitMenu quitMenu;
  public static DeathMenu deathMenu;

  public GameUIController(GameScene gameScene) {
    this.gameScene = gameScene;
  }
  public static GameUIController getGameUIController(){
    return controller;
  }
  @Override
  public void init() {
    controller=this;
    mainPane.setMinHeight(gameScene.getViewport().getHeight());
    mainPane.setMinWidth(gameScene.getViewport().getWidth());
    addHPBars();
    addButtons();
    characterMenu = new CharacterMenu(MCClient.getClientEntity(), controller);
    characterMenu.setMinHeight(FXGL.getGameScene().getViewport().getHeight());
    characterMenu.setMinWidth(FXGL.getGameScene().getViewport().getWidth());
    characterMenu.setDisable(true);
    characterMenu.setVisible(false);
    characterMenu.setScaleX(2.0);
    characterMenu.setScaleY(2.0);
    quitMenu = new QuitMenu();
    quitMenu.initialize();
    quitMenu.setMinHeight(gameScene.getViewport().getHeight());
    quitMenu.setMinWidth(gameScene.getViewport().getWidth());
    quitMenu.setVisible(false);
    quitMenu.setDisable(true);
    deathMenu = new DeathMenu();
    deathMenu.initialize();
    deathMenu.setMinHeight(gameScene.getViewport().getHeight());
    deathMenu.setMinWidth(gameScene.getViewport().getWidth());
    deathMenu.setDisable(true);
    deathMenu.setVisible(false);
    mainPane.getChildren().addAll(quitMenu,deathMenu);
  }

  public static void newCharacterMenu() {
    if (controller==null) return;
    controller.mainPane.getChildren().remove(characterMenu);
    characterMenu = new CharacterMenu(MCClient.getClientEntity(), controller);
    characterMenu.setMinHeight(FXGL.getGameScene().getViewport().getHeight());
    characterMenu.setMinWidth(FXGL.getGameScene().getViewport().getWidth());
    characterMenu.setDisable(true);
    characterMenu.setVisible(false);
    characterMenu.setScaleX(2.0);
    characterMenu.setScaleY(2.0);
    controller.mainPane.getChildren().add(characterMenu);
  }

  private void addButtons() {
    menu = new ImageButton("menu", new Image("assets/ui/elements/menu_selected.png"),
        new Image("assets/ui/elements/menu.png"));
    menu.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      quitMenu.setDisable(false);
      quitMenu.setVisible(true);
    });
    character = new ImageButton("character", new Image("assets/ui/elements/character_selected.png"),
        new Image("assets/ui/elements/character.png"));
    character.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      characterMenu.show();
    });
    buttonsBox.getChildren().add(character);
    buttonsBox.getChildren().add(menu);
  }

  private synchronized void addHPBars() {
    healthBarBox.getChildren().clear();
    List<Entity> entities = new java.util.ArrayList<>(queue.stream().toList());
    Entity client = entities.stream()
        .filter(e -> e.getComponent(DataComponent.class).getId() == MCClient.getClientId())
        .findFirst().get();
    entities.remove(client);
    healthBarBox.getChildren().add(client.getComponent(HPViewComponent.class).getHPBar());
    entities.forEach(entity -> healthBarBox.getChildren()
        .add(entity.getComponent(HPViewComponent.class).getHPBar()));

  }

  public void addHPBar(Entity entity) {
    healthBarBox.getChildren().add(entity.getComponent(HPViewComponent.class).getHPBar());
  }

  public volatile static BlockingQueue<Entity> queue = new LinkedBlockingQueue<>();

  public void removeHPBar(Entity entity) {
    healthBarBox.getChildren().remove(entity.getComponent(HPViewComponent.class).getHPBar());
  }
}
