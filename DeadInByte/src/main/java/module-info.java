module ru.nsu.fit.dib.deadinbyte {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.fit.dib.deadinbyte to javafx.fxml;
    exports ru.nsu.fit.dib.deadinbyte;
}