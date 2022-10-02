package ru.nsu.fit.dib.controller;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ru.nsu.fit.dib.service.GameService;
import ru.nsu.fit.dib.service.impl.GameServiceImpl;

public class PlayerController {

    private GameService gameService;
    @FXML
    private Rectangle player;

    private TranslateTransition transition;

    private Point2D vector;

    public void initialize() {
        gameService = new GameServiceImpl();
        vector = new Point2D(0, 0);
        transition = new TranslateTransition();
        transition.setNode(player);
        transition.setDuration(Duration.millis(100));
        transition.getDelay();
        transition.play();
    }

    public void moveW(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W) {
            vector = vector.add(0, 10);
            transition.setByY(vector.getY());
            transition.play();
        }
    }

    public void unMoveW(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W) {
            vector = vector.add(0, -10);
            transition.setByY(vector.getY());
        }
    }

    public void moveA(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.A) {
            vector = vector.add(-10, 0);
            transition.setByX(vector.getX());
            transition.play();
        }
    }

    public void unMoveA(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.A) {
            vector = vector.add(10, 0);
            transition.setByX(vector.getX());
        }
    }

    public void moveS(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.S) {
            vector = vector.add(0, -10);
            transition.setByY(vector.getY());
            transition.play();
        }
    }

    public void unMoveS(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.S) {
            vector = vector.add(0, 10);
            transition.setByY(vector.getY());
        }
    }

    public void moveD(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            vector = vector.add(10, 0);
            transition.setByX(vector.getX());
            transition.play();
        }
    }

    public void unMoveD(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            vector = vector.add(-10, 0);
            transition.setByX(vector.getX());
        }
    }

}