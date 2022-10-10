module ru.nsu.fit.dib.projectdib {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens ru.nsu.fit.dib.projectdib to javafx.fxml;
    exports ru.nsu.fit.dib.projectdib;
}