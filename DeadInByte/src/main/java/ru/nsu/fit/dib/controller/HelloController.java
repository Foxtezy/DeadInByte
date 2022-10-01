package ru.nsu.fit.dib.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ru.nsu.fit.dib.service.HelloService;
import ru.nsu.fit.dib.service.impl.HelloServiceImpl;

public class HelloController {

    private final HelloService helloService = new HelloServiceImpl();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onCalcButtonClick() {
        Integer x = helloService.plus(2, 2);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, x.toString());
        alert.show();
    }





}