package ru.nsu.fit.dib.projectdib.UI;


import static java.lang.Math.min;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.FXGLButton;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javafx.animation.Animation;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

//fx:controller="ru.nsu.fit.dib.projectdib.UI.Controller"
public class StartMenu extends FXGLMenu {

  public StartMenu(MenuType type) {
    super(type);
    Pane canvas = getContentRoot();
    canvas.setStyle("-fx-background-color: #121218;");

    StackPane mainStack = new StackPane();
    canvas.getChildren().add(mainStack);
    mainStack.setPrefSize(getAppWidth(), getAppHeight());
    //======================================[     Grid     ]=======================================
    GridPane fstGrid = new GridPane();
    GridPane sndGrid = new GridPane();
    {
      ColumnConstraints left1 = new ColumnConstraints();
      left1.setPercentWidth(50);
      ColumnConstraints right1 = new ColumnConstraints();
      right1.setPercentWidth(50);
      ColumnConstraints left2 = new ColumnConstraints();
      left2.setPercentWidth(40);
      ColumnConstraints right2 = new ColumnConstraints();
      right2.setPercentWidth(60);

      RowConstraints rc1 = new RowConstraints();
      rc1.setPercentHeight(100);
      RowConstraints rc2 = new RowConstraints();
      rc2.setPercentHeight(100);

      fstGrid.getColumnConstraints().addAll(left1, right1);
      fstGrid.getRowConstraints().add(rc1);
      sndGrid.getColumnConstraints().addAll(left2, right2);
      sndGrid.getRowConstraints().add(rc2);
    }
    mainStack.getChildren().addAll(sndGrid, fstGrid);

    VBox ui = new VBox();
    StackPane images = new StackPane();
    {
      fstGrid.add(ui, 0, 0);
      fstGrid.setCenterShape(true);
      fstGrid.setAlignment(Pos.CENTER);

      sndGrid.add(images, 1, 0);
      sndGrid.setCenterShape(true);
      sndGrid.setAlignment(Pos.CENTER);
    }
    //======================================[   Animation   ]=======================================
    final ImageView forest = initializeAnimationImageView("assets/UI/images/forest1k.png", 8640,
        720,
        true, false, Duration.millis(900), 9, 9, 0, 0);
    final ImageView campfire = initializeAnimationImageView("assets/UI/images/campfire1k.png", 8640,
        720, true, false, Duration.millis(900), 9, 9, 0, 0);
    forest.setPreserveRatio(true);
    forest.setViewport(new Rectangle2D(0, 0, 50d, 50d));
    campfire.setPreserveRatio(true);
    images.setAlignment(Pos.CENTER);
    images.getChildren().addAll(forest, campfire);
    //======================================[     Fonts     ]=======================================

    Font font = Font.loadFont("file:/F:/DeadInByte/src/main/resources/assets/UI/DustyPro.ttf", 80);
    Font bigFont = Font.loadFont("file:/F:/DeadInByte/src/main/resources/assets/UI/DustyPro.ttf",
        140);
    //=====================================[    GameName    ]=======================================

    AnchorPane name = new AnchorPane();
    VBox nameBox = new VBox();
    Text gameName = new Text("Roll Dice,");
    Text gameName2 = new Text("pls");
    gameName.setStyle("-fx-fill: #ffffff;");
    gameName2.setStyle("-fx-fill: #ffffff");
    gameName.setFont(bigFont);
    gameName2.setFont(font);

    nameBox.getChildren().addAll(gameName, gameName2);
    AnchorPane.setLeftAnchor(nameBox, 110d);
    AnchorPane.setTopAnchor(nameBox, 50d);

    name.getChildren().addAll(nameBox);
    canvas.getChildren().add(name);
    //=====================================[    Buttons     ]=======================================
    ui.setSpacing(40);

    Image unpushed = new Image("assets/UI/images/menu_button1k.png", 1020, 180, true, false);
    Image pushed = new Image("assets/UI/images/menu_selected_button1k.png", 1020, 180, true, false);

    ImageButton start = new ImageButton("Start", font, pushed, unpushed);
    start.setOnMouseClicked(event -> FXGL.getGameController());

    ImageButton multiplayer = new ImageButton("Multiplayer", font, pushed, unpushed);

    ImageButton settings = new ImageButton("Settings", font, pushed, unpushed);
    //ImageView x = new ImageView(new Image("assets/UI/images/menu_button1k.png"));

    ui.setAlignment(Pos.CENTER);
    Rectangle space = new Rectangle(10, 140, Paint.valueOf("transparent"));
    ui.getChildren().addAll(space, start, multiplayer, settings);
  }

  WrappedImageView initializeAnimationImageView(String url, double requestedWidth,
      double requestedHeight, boolean preserveRatio, boolean smooth, Duration duration, int columns,
      int count, int offsetX, int offsetY) {
    Image img = new Image(url, requestedWidth, requestedHeight, preserveRatio, smooth);
    final WrappedImageView view = new WrappedImageView(img);
    final Animation animation = new SpriteAnimation(view, duration, columns, count, offsetX,
        offsetY, (int) Math.round(img.getWidth() / columns), (int) Math.round(img.getHeight()));
    animation.setCycleCount(Animation.INDEFINITE);
    animation.play();
    return view;
  }
}