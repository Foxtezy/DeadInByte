package ru.nsu.fit.dib.projectdib.ui;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._campfireAnimationUI;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._fontDustyPro;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._forestAnimationUI;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._loadingAnimation;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._menuButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._menuSelectedButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._returnButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._returnSelectedButton;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.style;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.CompletableFuture;
import javafx.animation.Animation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.App;
import ru.nsu.fit.dib.projectdib.connecting.tasks.ClientConnectionTask;
import ru.nsu.fit.dib.projectdib.connecting.tasks.ServerConnectionThread;
import ru.nsu.fit.dib.projectdib.data.Config;
import ru.nsu.fit.dib.projectdib.data.Musics;
import ru.nsu.fit.dib.projectdib.data.ProjectConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ServerConfig;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.server.MCServer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Receiver;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;
import ru.nsu.fit.dib.projectdib.ui.UIElements.ImageButton;
import ru.nsu.fit.dib.projectdib.ui.UIElements.SpriteAnimation;
import ru.nsu.fit.dib.projectdib.ui.UIElements.WrappedImageView;
import ru.nsu.fit.dib.projectdib.utils.BackgroundMusicController;
import ru.nsu.fit.dib.projectdib.utils.SoundsController;

/**
 * Главное меню.
 */
public class MainMenu extends FXGLMenu {
  private static MainMenu mainMenu;
  private final Font font;
  private final ImageButton settings;
  private final AnchorPane globalAnchor;
  private final ImageButton returnButton;
  private final VBox ui;
  private final TreeNode<Node> tree;
  private final ImageButton start;

  public static SettingsMenu getSettingsMenu() {
    return menu;
  }

  public static SettingsMenu menu;

  public MainMenu(MenuType type) {
    super(type);
    BackgroundMusicController.getBackgroundMusicControlleroller().setMusic(Musics.menu);
    Pane canvas = getContentRoot();
    canvas.setStyle("-fx-background-color: #121218;");
    globalAnchor = new AnchorPane();
    globalAnchor.setPrefSize(getAppWidth(), getAppHeight());

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

    ui = new VBox();
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
    ImageView loading = new ImageView(new Image(_loadingAnimation));
    //======================================[     Fonts     ]=======================================
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    font = Font.loadFont(classloader.getResourceAsStream(_fontDustyPro), 65);
    Font bigFont = Font.loadFont(classloader.getResourceAsStream(_fontDustyPro), 140);
    Font smallFont = Font.loadFont(classloader.getResourceAsStream(_fontDustyPro), 50);
    //=====================================[    GameName    ]=======================================

    AnchorPane name = new AnchorPane();
    VBox nameBox = new VBox();
    Text gameName = new Text("DEAD In ByTE");
    Text gameName2 = new Text("THe LAsT DunGeon");
    gameName.setStyle("-fx-fill: #ffffff;");
    gameName2.setStyle("-fx-fill: #ffffff");
    gameName.setFont(bigFont);
    gameName2.setFont(font);

    nameBox.getChildren().addAll(gameName, gameName2);
    AnchorPane.setLeftAnchor(nameBox, 110d);
    AnchorPane.setTopAnchor(nameBox, 50d);

    name.getChildren().addAll(nameBox);
    canvas.getChildren().addAll(name,globalAnchor);
    //=====================================[    Buttons     ]=======================================
    ui.setSpacing(40);
    Image unpushed = new Image(_menuButton, 1020, 180, true, false);
    Image pushed = new Image(_menuSelectedButton, 1020, 180, true, false);
    Image unpushedEnter = new Image(_menuButton, 510, 90, true, false);
    Image pushedEnter = new Image(_menuSelectedButton, 510, 90, true, false);
    Image unpushedServer = new Image(_menuButton, 765, 135, true, false);
    Image pushedServer = new Image(_menuSelectedButton, 765, 135, true, false);
    Image unpushedUpdate = new Image(_menuButton, 510, 90, true, false);
    Image pushedUpdate = new Image(_menuSelectedButton, 510, 90, true, false);
    Image unpushedReturn = new Image(_returnButton, 132, 132, true, false);
    Image pushedReturn = new Image(_returnSelectedButton, 132, 132, true,
        false);
    //==============================================================================================
    start = new ImageButton("Start", font, "#5ae8a8", "#2b2944", pushed, unpushed);
    ImageButton multiplayer = new ImageButton("Multiplayer", font, "#5ae8a8", "#2b2944", pushed,
        unpushed);
    settings = new ImageButton("Settings", font, "#5ae8a8", "#2b2944", pushed,
        unpushed);
    //==============================================================================================
    ImageButton connect = new ImageButton("Connect", font, "#5ae8a8", "#2b2944", pushed, unpushed);
    ImageButton server = new ImageButton("Create server", font, "#5ae8a8", "#2b2944", pushed,
        unpushed);
    String gamePort = String.valueOf(Config.PORT);
    ImageButton startMultiplayer = new ImageButton("Start", smallFont, "#5ae8a8", "#2b2944",
        pushedServer,
        unpushedServer);
    ImageButton update = new ImageButton("Update", smallFont, "#5ae8a8", "#2b2944",
        pushedUpdate,
        unpushedUpdate);
    ImageButton enter = new ImageButton("Enter", font, "#5ae8a8", "#2b2944", pushedEnter,
        unpushedEnter);
    //==============================================================================================
    returnButton = new ImageButton("", font, "#5ae8a8", "#2b2944", pushedReturn,
        unpushedReturn);
    //==============================================================================================
    ui.setAlignment(Pos.CENTER);
    Rectangle space = new Rectangle(10, 140, Paint.valueOf("transparent"));
    //===
    ui.getChildren().addAll(space, start, multiplayer, settings);
    //=====================================[   Buttons Tree   ]=====================================
    ///root///
    tree = new TreeNode<>(null, List.of(start, multiplayer, settings));
    //=====================================[ Buttons Handlers ]=====================================

    //===Multiplayer===
    multiplayer.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      globalAnchor.getChildren().add(returnButton);
      ui.getChildren().removeAll(tree.getANChildren());
      tree.changeActiveNode(multiplayer);
      tree.addNodes(multiplayer, List.of(connect, server));
      ui.getChildren().addAll(tree.getANChildren());
    });

    //===Create server===
    VBox serverBox = new VBox();
    serverBox.setStyle("-fx-padding: 100;");
    //update.setStyle("-fx-padding: 20");
    startMultiplayer.setStyle("-fx-padding: 20");
    //serverID.setStyle("-fx-padding: 20");
    Text serverid = new Text("Port: " + gamePort);
    serverid.setStyle("-fx-fill: #5ae8a8;");
    serverid.setFont(smallFont);
    serverBox.getChildren().addAll(update, startMultiplayer, serverid);
    ScrollPane scrollPane = new ScrollPane();
    server.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      ServerConfig.addServerConnectionThread(new ServerConnectionThread());
      ui.getChildren().removeAll(tree.getANChildren());
      tree.changeActiveNode(server);
      scrollPane.setPrefViewportHeight(600);
      scrollPane.setContent(serverBox);
      scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
      scrollPane.getStylesheets().add(
          Objects.requireNonNull(this.getClass().getClassLoader().getResource(style))
              .toExternalForm());
      tree.addNodes(server, List.of(scrollPane));
      ui.getChildren().addAll(tree.getANChildren());
    });

    //===Update===
    update.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      ui.getChildren().removeAll(tree.getANChildren());
      tree.removeChildren();
      var clients = MCServer.getConnectionThread().getClientSockets();
      for (Entry<Integer, Socket> s : clients.entrySet()) {
        ImageButton newClient = new ImageButton(
            s.getValue().getInetAddress().toString().replaceFirst("/", ""), font, "#5ae8a8",
            "#2b2944",
            pushedServer,
            unpushedServer);
        if (!serverBox.getChildren().contains(newClient)) {
          serverBox.getChildren().add(newClient);
        }
      }
      scrollPane.setContent(serverBox);
      tree.addNodes(server, List.of(scrollPane));
      ui.getChildren().addAll(tree.getANChildren());
    });

    //===Start Multiplayer===
    startMultiplayer.setOnMouseClicked(e -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      // шлём инициализационный пакет клиентам
      Sender sender = new Sender();
      try {
        sender.send(MCServer.getClientSockets().get(1), new Pair<>(MessageType.START_GAME, null));
      } catch (IOException ex) {

      }
      // читаем у локального клиента
      Receiver receiver = new Receiver(MCClient.getClientSocket());
      try {
        receiver.receive();
      } catch (IOException ex) {
        App.stop();
      }
      FXGL.getGameController().startNewGame();
    });

    //===Connect===
    TextField passwordField = new TextField();
    VBox authentication = new VBox();
    passwordField.setPrefSize(700, 100);
    passwordField.setMinSize(700, 100);
    passwordField.setMaxSize(700, 100);
    passwordField.setFont(smallFont);
    passwordField.setAlignment(Pos.CENTER);
    passwordField.setPromptText("Enter game address");
    passwordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%)");
    authentication.setAlignment(Pos.CENTER);
    authentication.getChildren().add(passwordField);
    authentication.getChildren().add(enter);

    connect.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      ui.getChildren().removeAll(tree.getANChildren());
      tree.changeActiveNode(connect);
      tree.addNodes(connect, List.of(authentication));
      ui.getChildren().add(authentication);
    });

    //===Enter===
    Text text1 = new Text("Please, wait for");
    Text text2 = new Text("the game to start");
    text1.setFont(smallFont);
    text2.setFont(smallFont);
    text1.setFill(Color.WHITE);
    text2.setFill(Color.WHITE);
    VBox loadingBox = new VBox();
    loadingBox.getChildren().add(loading);
    loadingBox.getChildren().add(text1);
    loadingBox.getChildren().add(text2);
    loadingBox.setAlignment(Pos.CENTER);

    enter.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      SocketAddress socketAddress;
      try {
        socketAddress = new InetSocketAddress(passwordField.getText().split(":")[0],
            Integer.parseInt(passwordField.getText().split(":")[1]));
      } catch (Exception e) {
        passwordField.clear();
        passwordField.setPromptText("Wrong address!");
        passwordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%)");
        return;
      }
      Future<Socket> clientFuture = CompletableFuture.supplyAsync(
          new ClientConnectionTask(socketAddress));
      try {
        if (clientFuture.get() == null) {
          passwordField.clear();
          passwordField.setPromptText("Wrong address!");
          passwordField.setStyle(
              "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%)");
        } else {
          ui.getChildren().removeAll(tree.getANChildren());
          tree.changeActiveNode(authentication);
          tree.addNodes(authentication, List.of(loadingBox));
          ui.getChildren().add(loadingBox);
          Receiver receiver = new Receiver(MCClient.getClientSocket());
          while (true) {
            MessageType startGame = receiver.receive().getKey();
            if (startGame == MessageType.START_GAME) {
              break;
            }
          }
          FXGL.getGameController().startNewGame();
        }
      } catch (InterruptedException | ExecutionException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        App.stop();
      }
    });

    menu = new SettingsMenu();
    AnchorPane.setTopAnchor(menu,20.0);
    AnchorPane.setBottomAnchor(menu,20.0);
    AnchorPane.setRightAnchor(menu,20.0);
    AnchorPane.setLeftAnchor(menu,20.0);
    menu.setMinSize(globalAnchor.getMinWidth(), globalAnchor.getMinWidth());
    globalAnchor.getChildren().add(menu);
    menu.setVisible(false);
    menu.setDisable(true);
    //===Settings===
    settings.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
      menu.setVisible(true);
      menu.setDisable(false);
    });


    //===Start===
    start.setOnMouseClicked(event -> {
      SoundsController.getSoundsController().play(Sounds.select_button);
          ServerConfig.addServerConnectionThread(new ServerConnectionThread());
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          MCServer.getConnectionThread().interrupt();
          FXGL.getGameController().startNewGame();
        }
    );
    //===Return===
    returnButton.setOnMouseClicked(event -> {
      returnBack();
    });
    AnchorPane.setBottomAnchor(returnButton, 100d);
    AnchorPane.setRightAnchor(returnButton, 120d);
    //============================================================================================

  }

  public void returnBack() {
    if (ui.getChildren().contains(start)) return;
    SoundsController.getSoundsController().play(Sounds.select_button);
    ui.getChildren().removeAll(tree.getANChildren());
    tree.removeChildren();
    tree.changeActiveNode(tree.getParentA());
    if (tree.getRoot() == tree.getParentAN()) {
      globalAnchor.getChildren().remove(returnButton);
    }
    ui.getChildren().addAll(tree.getANChildren());
  }

  public static MainMenu getMainMenu() {
    return mainMenu;
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

  public void init() {
    mainMenu = this;
  }
}