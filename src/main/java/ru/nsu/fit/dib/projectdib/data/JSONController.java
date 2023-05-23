package ru.nsu.fit.dib.projectdib.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JSONController {
   public static <T> T load(String path,Class<?> objectClass){
    File file = new File(path);
     T object = null;
    try {
      if (!file.exists()) {
        return null;
      }
      BufferedReader reader = new BufferedReader(new FileReader(file));
      object = new Gson().<T>fromJson(reader, objectClass);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return object;
  }
  public static <T> void save (String path,Object object){
    try (Writer writer = new FileWriter(path)){
      String str = new Gson().toJson(object);
      writer.write(str);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
