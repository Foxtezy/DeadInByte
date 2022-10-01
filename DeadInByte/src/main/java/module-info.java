module ru.nsu.fit.dib.deadinbyte {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens ru.nsu.fit.dib.deadinbyte to javafx.fxml;
    exports ru.nsu.fit.dib.deadinbyte;
}