package ru.nsu.fit.dib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.nsu.fit.dib.controller.PlayerController;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        PlayerController playerController = fxmlLoader.getController();
        playerController.initialize();

        stage.setTitle("TestGame");
        stage.setScene(scene);

        stage.addEventHandler(KeyEvent.KEY_PRESSED, playerController::moveA);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, playerController::moveW);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, playerController::moveD);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, playerController::moveS);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, playerController::unMoveA);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, playerController::unMoveW);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, playerController::unMoveD);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, playerController::unMoveS);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}