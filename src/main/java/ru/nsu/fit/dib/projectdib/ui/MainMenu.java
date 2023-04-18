package ru.nsu.fit.dib.projectdib.ui;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._campfireAnimationUI;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._fontDustyPro;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._forestAnimationUI;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._menuButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._menuSelectedButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._returnButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._returnSelectedButton;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import javafx.animation.Animation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.App;
import ru.nsu.fit.dib.projectdib.GameMode;
import ru.nsu.fit.dib.projectdib.connecting.tasks.ServerConnectionTask;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.ui.UIElements.ImageButton;
import ru.nsu.fit.dib.projectdib.ui.UIElements.SpriteAnimation;
import ru.nsu.fit.dib.projectdib.ui.UIElements.WrappedImageView;

/**
 * Главное меню.
 */
public class MainMenu extends FXGLMenu {

  public MainMenu(MenuType type) {
    super(type);
    Pane canvas = getContentRoot();
    canvas.setStyle("-fx-background-color: #121218;");
    AnchorPane globalAnchor = new AnchorPane();
    globalAnchor.setPrefSize(getAppWidth(), getAppHeight());
    canvas.getChildren().addAll(globalAnchor);

    StackPane mainStack = new StackPane();
    AnchorPane.setTopAnchor(mainStack, 0d);
    AnchorPane.setBottomAnchor(mainStack, 0d);
    AnchorPane.setLeftAnchor(mainStack, 0d);
    AnchorPane.setRightAnchor(mainStack, 0d);

    globalAnchor.getChildren().add(mainStack);
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
    final ImageView forest = initializeAnimationImageView(_forestAnimationUI, 8640,
        720,
        true, false, Duration.millis(900), 9, 9, 0, 0);
    final ImageView campfire = initializeAnimationImageView(_campfireAnimationUI, 8640,
        720, true, false, Duration.millis(900), 9, 9, 0, 0);
    forest.setPreserveRatio(true);
    forest.setViewport(new Rectangle2D(0, 0, 50d, 50d));
    campfire.setPreserveRatio(true);
    images.setAlignment(Pos.CENTER);
    images.getChildren().addAll(forest, campfire);
    //======================================[     Fonts     ]=======================================
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    Font font = Font.loadFont(classloader.getResourceAsStream(_fontDustyPro), 65);
    Font bigFont = Font.loadFont(classloader.getResourceAsStream(_fontDustyPro), 140);
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

    Image unpushed = new Image(_menuButton, 1020, 180, true, false);
    Image pushed = new Image(_menuSelectedButton, 1020, 180, true, false);
    Image unpushedReturn = new Image(_returnButton, 132, 132, true, false);
    Image pushedReturn = new Image(_returnSelectedButton, 132, 132, true,
        false);
    //==============================================================================================
    ImageButton start = new ImageButton("Start", font, "#5ae8a8", "#2b2944", pushed, unpushed);
    ImageButton multiplayer = new ImageButton("Multiplayer", font, "#5ae8a8", "#2b2944", pushed,
        unpushed);
    ImageButton settings = new ImageButton("Settings", font, "#5ae8a8", "#2b2944", pushed,
        unpushed);
    //==============================================================================================
    ImageButton connect = new ImageButton("Connect", font, "#5ae8a8", "#2b2944", pushed, unpushed);
    ImageButton server = new ImageButton("Create server", font, "#5ae8a8", "#2b2944", pushed,
        unpushed);
    //==============================================================================================
    ImageButton returnButton = new ImageButton("", font, "#5ae8a8", "#2b2944", pushedReturn,
        unpushedReturn);
    //==============================================================================================
    ui.setAlignment(Pos.CENTER);
    Rectangle space = new Rectangle(10, 140, Paint.valueOf("transparent"));
    //===
    ui.getChildren().addAll(space, start, multiplayer, settings);
    //=====================================[   Buttons Tree   ]=====================================
    TreeNode<ImageButton> tree = new TreeNode<>(null, List.of(start, multiplayer, settings));
    tree.addNodes(multiplayer, List.of(connect, server));
    //=====================================[ Buttons Handlers ]=====================================
    //===Multiplayer===
    multiplayer.setOnMouseClicked(event -> {
      globalAnchor.getChildren().add(returnButton);
      ui.getChildren().removeAll(tree.getParentANChildren());
      tree.changeActiveNode(multiplayer);
      ui.getChildren().addAll(tree.getANChildren());
    });
    //===Start===
    start.setOnMouseClicked(event -> {
      App.gameMode = GameMode.SINGLE;
      FXGL.getGameController().startNewGame();
    });
    //===Server===
    server.setOnMouseClicked(event -> {
      App.gameMode = GameMode.SERVER;
      FXGL.getGameController().startNewGame();
    });
    //===Client===
    connect.setOnMouseClicked(event -> {
      App.gameMode = GameMode.CLIENT;
      FXGL.getGameController().startNewGame();
    });
    //===Settings===
    settings.setOnMouseClicked(event -> {
    });
    //==Server==
    //AtomicReference<CompletableFuture<List<SocketAddress>>> serverConnectionFuture = new AtomicReference<>();
    //ServerConnectionTask serverConnectionTask = new ServerConnectionTask(MCServer.getServerSocket());
    //server.setOnMouseClicked(event -> serverConnectionFuture.set(CompletableFuture.supplyAsync(serverConnectionTask)));
    //===Return===
    returnButton.setOnMouseClicked(event -> {
      ui.getChildren().removeAll(tree.getParentANChildren());
      tree.changeActiveNode(tree.getRoot());
      if (tree.getRoot() == tree.getParentAN()) {
        globalAnchor.getChildren().remove(returnButton);
      }
      ui.getChildren().addAll(tree.getANChildren());
    });
    AnchorPane.setBottomAnchor(returnButton, 100d);
    AnchorPane.setRightAnchor(returnButton, 120d);
    //============================================================================================

  }

  private WrappedImageView initializeAnimationImageView(String url, double requestedWidth,
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