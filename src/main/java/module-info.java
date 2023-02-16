open module projectdib.main {
    requires com.almasb.fxgl.all;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
  requires annotations;
  requires kotlin.stdlib;
  exports ru.nsu.fit.dib.projectdib;
  exports ru.nsu.fit.dib.projectdib.ui;
  exports ru.nsu.fit.dib.projectdib.ui.UIElements;
  exports ru.nsu.fit.dib.projectdib.data;
  exports ru.nsu.fit.dib.projectdib.init_app;
}
/*
module ru.nsu.fit.dib.projectdib {




}
 */