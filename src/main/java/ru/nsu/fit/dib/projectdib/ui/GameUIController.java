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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HPViewComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

/**
 * Пока не знаю зачем, но пусть будет. По идее нужен для обработки событий кнопок. Но что то пошло
 * не так...
 */
public final class GameUIController implements UIController {
  @FXML
  public VBox healthBarBox;
  @FXML
  public ImageView esc;
  @FXML
  private final GameScene gameScene;

  public GameUIController(GameScene gameScene) {
    this.gameScene = gameScene;
  }

  @Override
  public void init() {
    updateHPBarList();
  }

  public synchronized void updateHPBarList() {
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
