package ru.nsu.fit.dib.projectdib.ui;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._fontAgencyFB;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._fontDustyPro;

import com.almasb.fxgl.core.util.Platform;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.UIController;
import java.io.IOException;
import java.net.URL;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.nsu.fit.dib.projectdib.utils.BackgroundMusicController;
import ru.nsu.fit.dib.projectdib.utils.SoundsController;

public class SettingsMenu extends AnchorPane {

  @FXML
  StackPane movementPane;
  @FXML
  StackPane stackPane;
  @FXML
  public Slider musicSlider;
  @FXML
  public Slider soundsSlider;
  @FXML
  public ImageView close;
  private Runnable closeEvent;

  public SettingsMenu() {
    FXMLLoader loader = null;
    loader = new FXMLLoader(getClass().getClassLoader().getResource("assets/ui/Settings.fxml"));

    loader.setRoot(this);
    loader.setController( this );

    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Font bigFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream(_fontAgencyFB), 65);
    Font font = Font.loadFont(getClass().getClassLoader().getResourceAsStream(_fontAgencyFB), 50);
    stackPane.getChildren().forEach(c-> {
      if (c instanceof Text) {
        ((Text)c).setFont(bigFont);
      }
    });
    movementPane.getChildren().forEach(c-> {
      if (c instanceof Text) {
        ((Text)c).setFont(font);
      }
    });
    musicSlider.setMin(0.0);
    System.out.println(BackgroundMusicController.getBackgroundMusicControlleroller().getVolume());
    musicSlider.setValue(BackgroundMusicController.getBackgroundMusicControlleroller().getVolume());
    musicSlider.setMax(1.0);
    musicSlider.valueProperty().addListener((ObservableValue<? extends  Number> num,Number old,Number newVal)->{
      BackgroundMusicController.getBackgroundMusicControlleroller().setVolume((double) newVal);
    });
    soundsSlider.setMin(0.0);
    soundsSlider.setValue(SoundsController.getSoundsController().getVolume());
    soundsSlider.setMax(1.0);
    soundsSlider.valueProperty().addListener((ObservableValue<? extends  Number> num,Number old,Number newVal)->{
      SoundsController.getSoundsController().setVolume((double) newVal);
    });
    close.onMouseClickedProperty().set(event -> {
      this.setVisible(false);
      this.setDisable(true);
      if (closeEvent!=null) closeEvent.run();
    });
  }
  public void setCloseEvent(Runnable event){
    closeEvent = event;
  }
}
