package ru.nsu.fit.dib.projectdib.ui;

import com.almasb.fxgl.entity.Entity;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HeroViewComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;

public class CharacterMenu extends AnchorPane {

  private final Entity player;
  public Text str;
  public Text agil;
  public Text dur;
  public Text intel;
  public Text wis;
  public Text charis;
  public ImageView close;
  public ImageView hero;
  private final GameUIController gameUIController;

  public CharacterMenu(Entity player, GameUIController gameUIController){
    this.player = player;
    hero = player.getComponent(HeroViewComponent.class).getView();
    this.gameUIController = gameUIController;
    FXMLLoader loader = null;
    loader = new FXMLLoader(getClass().getClassLoader().getResource("assets/ui/charactermenu.fxml"));

    loader.setRoot( this );
    loader.setController( this );

    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    close.setOnMouseClicked(event -> closeWindow());
  }

  public void show(){
    Creature creature = player.getComponent(CreatureComponent.class).getCreature();
    str.setText(((Integer) creature.getStat("strength")).toString());
    agil.setText(((Integer) creature.getStat("agility")).toString());
    dur.setText(((Integer) creature.getStat("durability")).toString());
    intel.setText(((Integer) creature.getStat("intelligence")).toString());
    wis.setText(((Integer) creature.getStat("wisdom")).toString());
    charis.setText(((Integer) creature.getStat("charisma")).toString());
    this.setDisable(false);
    this.setVisible(true);
  }
  public void closeWindow(){
    this.setVisible(false);
    this.setDisable(true);
    gameUIController.menu.setDisable(false);
    gameUIController.character.setDisable(false);
  }

}
