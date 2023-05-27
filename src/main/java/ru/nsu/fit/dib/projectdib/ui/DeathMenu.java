package ru.nsu.fit.dib.projectdib.ui;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._character_file;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._fontDustyPro;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._menuButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._menuSelectedButton;
import static ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner.doAction;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.App;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.data.HeroData;
import ru.nsu.fit.dib.projectdib.data.JSONController;
import ru.nsu.fit.dib.projectdib.data.Musics;
import ru.nsu.fit.dib.projectdib.data.RandomCharacterSystem;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.initapp.GameInitializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.EntitySpawner;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.ui.UIElements.ImageButton;
import ru.nsu.fit.dib.projectdib.utils.BackgroundMusicController;
import ru.nsu.fit.dib.projectdib.utils.SoundsController;

public class DeathMenu extends AnchorPane {

  private final ImageButton restart;
  private final ImageButton quit;
  private final VBox box;

  public DeathMenu() {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    Font font = Font.loadFont(classloader.getResourceAsStream(_fontDustyPro), 65);
    Image unpushed = new Image(_menuButton, 1020, 180, true, false);
    Image pushed = new Image(_menuSelectedButton, 1020, 180, true, false);
    restart = new ImageButton("restart", font, "#5ae8a8", "#2b2944", pushed, unpushed);
    quit = new ImageButton("quit", font, "#5ae8a8", "#2b2944", pushed, unpushed);
    box = new VBox();
    box.setAlignment(Pos.CENTER);
    box.setSpacing(40);
    AnchorPane.setBottomAnchor(box, 20.0);
    AnchorPane.setLeftAnchor(box, 20.0);
    AnchorPane.setRightAnchor(box, 20.0);
    AnchorPane.setTopAnchor(box, 20.0);
    box.getChildren().addAll(restart, quit);
  }

  public void initialize() {
    this.setStyle("-fx-background-color: rgba(0,0,0,0.4);"
        + "-fx-padding: 20;");
    this.getChildren().add(box);

    restart.setOnMouseClicked(event -> {
      HeroData data = new HeroData(RandomCharacterSystem.NewCharacter(), RandomSystem.getRandInt(10000));
      JSONController.save(_character_file,data);
      Creature creature = HeroesFactory.newHero(data.getType(),data.getSeed());
      EntitySpawner.doAction(new Pair<>(MessageType.SPAWN, new SpawnAction(new NewEntity(creature.getStandardWeapon().getName(), data.getSeed(), new EntityState(-1, GameInitializer.start, new Point2D(0,0), -1)))));
      doAction(new Pair<>(MessageType.SPAWN, new SpawnAction(
          new NewEntity(data.getType().getName(), data.getSeed(), GameInitializer.start, null))));
      this.setVisible(false);
      this.setDisable(true);
    });
    quit.setOnMouseClicked(event -> {
      BackgroundMusicController.getBackgroundMusicControlleroller().setMusic(Musics.menu);
      SoundsController.getSoundsController().play(Sounds.select_button);
      MainMenu.getMainMenu().returnBack();
      MainMenu.getMainMenu().returnBack();
      App.stop();
      this.setVisible(false);
      this.setDisable(true);
    });
  }
}
