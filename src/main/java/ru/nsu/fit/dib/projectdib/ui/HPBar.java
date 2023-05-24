package ru.nsu.fit.dib.projectdib.ui;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ru.nsu.fit.dib.projectdib.entity.components.data.CreatureComponent;

public class HPBar extends HBox {
  @FXML
  public ProgressBar hpBar;
  @FXML
  public ImageView icon;
  private double persent;
  private double maxHP;

  public HPBar(int maxValue){
    maxHP=maxValue;
    persent = (double) maxValue/100;
    FXMLLoader loader = null;
    loader = new FXMLLoader(getClass().getClassLoader().getResource("assets/ui/HPBar.fxml"));

    loader.setRoot( this );
    loader.setController( this );

    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  public void setIcon(Image image){
    icon.setImage(image);
  }
  public void setScale(double scale){
    icon.setScaleX(scale);
    icon.setScaleY(scale);
  }
  public void setViewport(Rectangle2D viewport){
    icon.setViewport(viewport);
  }
  public void setProgress(double progress){
    hpBar.setProgress(progress);
  }
  public ImageView getIcon() {
    return icon;
  }

  public ProgressBar getHpBar() {
    return hpBar;
  }

  public void setValue(int value) {
    hpBar.setProgress(((double)value*persent)/10);
  }
}
