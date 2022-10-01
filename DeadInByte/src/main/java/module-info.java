module ru.nsu.fit.dib {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.fit.dib to javafx.fxml;
    exports ru.nsu.fit.dib;
    exports ru.nsu.fit.dib.controller;
    exports ru.nsu.fit.dib.service;
    opens ru.nsu.fit.dib.controller to javafx.fxml;
}