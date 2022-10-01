package ru.nsu.fit.dib.deadinbyte;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import ru.nsu.fit.dib.deadinbyte.model.Body;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        Body body = new Body();
        body.setCoordinates(new Point2D(1, 2));

    }
}