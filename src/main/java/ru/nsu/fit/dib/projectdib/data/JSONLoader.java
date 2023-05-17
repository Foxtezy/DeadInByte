package ru.nsu.fit.dib.projectdib.data;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.CONTROLS_PATH;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONLoader {
  public static void load(String path, Class<?> loadedClass){
    File file = new File(path);
    try {
      if (!file.exists()) {
        throw new RuntimeException("controls.json not found");
      }
      BufferedReader reader = new BufferedReader(new FileReader(file));
      new Gson().fromJson(reader, loadedClass);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
