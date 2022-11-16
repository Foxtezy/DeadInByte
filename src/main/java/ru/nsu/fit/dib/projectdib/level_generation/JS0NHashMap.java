package ru.nsu.fit.dib.projectdib.level_generation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;

public class JS0NHashMap {

  String pathName;
  HashMap<String, Integer> hashMap;

  public JS0NHashMap(String name) {
    this.pathName = name;
    hashMap = new HashMap<>();
  }

  /**
   * Write HashMap to json
   */
  public void serializeHashMap() {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    java.lang.String paletteStr = gson.toJson(hashMap);
    try (Writer writer = new FileWriter(pathName)) {
      writer.write(paletteStr);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Read HashMap from json
   */
  public void deserializeHashMap() {
    Gson gson = new GsonBuilder()
        .create();
    try {
      Reader reader = new FileReader(pathName);
      Type type = new TypeToken<HashMap<String, Integer>>() {
      }.getType();
      hashMap = gson.fromJson(reader, type);
      reader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Integer get(String name) {
    return hashMap.get(name);
  }
}
