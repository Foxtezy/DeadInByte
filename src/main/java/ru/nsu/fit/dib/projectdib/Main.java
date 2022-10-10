package ru.nsu.fit.dib.projectdib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
  //запуск
  public static void main(String[] args) {
    launch();
  }

  // Стадия заготовки окна
  @Override
  public void start(Stage stage) throws IOException {

    /*
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));  // загружаем разметку
    Scene scene = new Scene(fxmlLoader.load(), 320, 240); // устанавливаем размер окна
    stage.setTitle("Hello!"); // название
    stage.setScene(scene); // устанавливаем текущей нашу сцену
    stage.show(); // показать
     */
  }
}
