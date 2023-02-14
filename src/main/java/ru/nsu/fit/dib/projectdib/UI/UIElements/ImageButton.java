package ru.nsu.fit.dib.projectdib.UI.UIElements;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ImageButton  extends StackPane {
  ImageView iv;
  Text text;

  public ImageButton(String name, Font font, final Image pushed, final Image unpushed) {
    iv = new ImageView(unpushed);
    iv.autosize();
    iv.setPreserveRatio(true);
    text = new Text(name);
    text.setStyle("-fx-fill: #ffffff");
    text.setFont(font);

    this.setOnMouseEntered(event-> {
      iv.setImage(pushed);
      text.setStyle("-fx-scale-x: 1.1;"
              + "-fx-scale-y: 1.1;"
              + "-fx-scale-z: 1.1;"
              + "-fx-fill: #2b2944");
      iv.setStyle("-fx-scale-x: 1.1;"
          + "-fx-scale-y: 1.1;"
          + "-fx-scale-z: 1.1;");
    });
    this.setOnMouseExited(event-> {
      iv.setImage(unpushed);
      text.setStyle("-fx-scale-x: 1.0;"
          + "-fx-scale-y: 1.0;"
          + "-fx-scale-z: 1.0;"
          + "-fx-fill: #ffffff");
      iv.setStyle("-fx-scale-x: 1.0;"
          + "-fx-scale-y: 1.0;"
          + "-fx-scale-z: 1.0;");
    });

    super.setCenterShape(true);
    super.setAlignment(Pos.CENTER);
    super.getChildren().addAll(iv,text);
  }
  public void setImage(Image image){
    iv.setImage(image);
  }
}
