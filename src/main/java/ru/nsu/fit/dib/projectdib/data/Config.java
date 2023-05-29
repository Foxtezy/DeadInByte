package ru.nsu.fit.dib.projectdib.data;

import java.io.FileWriter;
import java.util.Objects;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Config.class retrieves settings data from the .ini file.
 */
public class Config {

  public static final javafx.util.Duration SHOOT_DELAY_ARROW = Duration.seconds(0.3);
  public static final javafx.util.Duration SHOOT_DELAY_AK = Duration.seconds(0.1);
  public static int WINDOW_HEIGHT;
  public static int WINDOW_WIDTH;
  public static String WINDOW_MODE;
  public static Integer KARMA;
  public static Double MUSIC;
  public static Double SOUNDS;
  public static int PORT;
  public static Properties props;
  public static String path;
  public static void setConfig(String filepath) {
    props = new Properties();
    path = filepath;
    try {
      props.load(new FileInputStream(filepath));

      WINDOW_WIDTH = Integer.parseInt(props.getProperty("WINDOW_WIDTH", "1280"));
      WINDOW_HEIGHT = Integer.parseInt(props.getProperty("WINDOW_HEIGHT", "720"));
      WINDOW_MODE = props.getProperty("WINDOW_MODE", "Window");
      KARMA = Integer.parseInt(props.getProperty("KARMA", "0"));
      MUSIC = Double.parseDouble(props.getProperty("MUSIC","0.5"));
      SOUNDS = Double.parseDouble(props.getProperty("SOUNDS","0.5"));
      PORT = Integer.parseInt(props.getProperty("PORT","8080"));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  public static void addProperty(String key, Object value){
    props.put(key, value.toString());
  }
  public static void saveSettings(){
    try {
      props.store(new FileWriter(path),"" );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
