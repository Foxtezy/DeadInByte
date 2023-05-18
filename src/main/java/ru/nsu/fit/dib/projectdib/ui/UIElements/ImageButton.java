package ru.nsu.fit.dib.projectdib.ui.UIElements;

import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Кнопка-картинка. Желательно конечно сделать ее более общей, однако пока что анимация наведения
 * задана по умолчанию.
 */
public class ImageButton extends StackPane {

  ImageView iv;
  Text text;
  public ImageButton(String name, Font font, String unpushedColor, String pushedColor,
      final Image pushed, final Image unpushed) {
    iv = new ImageView(unpushed);
    iv.autosize();
    iv.setPreserveRatio(true);
    text = new Text(name);
    text.setStyle("-fx-fill: " + unpushedColor + ";");
    text.setFont(font);

    this.setOnMouseEntered(event -> {
      iv.setImage(pushed);
      text.setStyle("-fx-scale-x: 1.1;"
          + "-fx-scale-y: 1.1;"
          + "-fx-scale-z: 1.1;"
          + "-fx-fill: " + pushedColor + ";");
      iv.setStyle("-fx-scale-x: 1.1;"
          + "-fx-scale-y: 1.1;"
          + "-fx-scale-z: 1.1;");
    });
    this.setOnMouseExited(event -> {
      iv.setImage(unpushed);
      text.setStyle("-fx-scale-x: 1.0;"
          + "-fx-scale-y: 1.0;"
          + "-fx-scale-z: 1.0;"
          + "-fx-fill: " + unpushedColor + ";");
      iv.setStyle("-fx-scale-x: 1.0;"
          + "-fx-scale-y: 1.0;"
          + "-fx-scale-z: 1.0;");
    });

    super.setCenterShape(true);
    super.setAlignment(Pos.CENTER);
    super.getChildren().addAll(iv, text);
  }
  public ImageButton(String buttonName,final Image pushed, final Image unpushed) {
    iv = new ImageView(unpushed);
    iv.autosize();
    iv.setPreserveRatio(true);
    text = new Text(buttonName);
    this.setOnMouseEntered(event -> {
      iv.setImage(pushed);
      iv.setStyle("-fx-scale-x: 1.1;"
          + "-fx-scale-y: 1.1;"
          + "-fx-scale-z: 1.1;");
    });
    this.setOnMouseExited(event -> {
      iv.setImage(unpushed);
      iv.setStyle("-fx-scale-x: 1.0;"
          + "-fx-scale-y: 1.0;"
          + "-fx-scale-z: 1.0;");
    });

    super.setCenterShape(true);
    super.setAlignment(Pos.CENTER);
    super.getChildren().add(iv);
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ImageButton that)) {
      return false;
    }
    return text.getText().equals(that.text.getText());
  }

  @Override
  public int hashCode() {
    return Objects.hash(text.getText());
  }
}
