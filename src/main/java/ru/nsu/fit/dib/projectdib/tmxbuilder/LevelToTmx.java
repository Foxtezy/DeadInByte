package ru.nsu.fit.dib.projectdib.tmxbuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import ru.nsu.fit.dib.projectdib.level_generation.BlockDensity;
import ru.nsu.fit.dib.projectdib.level_generation.JS0NHashMap;
import ru.nsu.fit.dib.projectdib.level_generation.Level;

public class LevelToTmx {

  private LevelToTmx() {
  }

  public static String levelToTmx(Level level) {
    String tmxName = "level.tmx";
    String tmxPath = LevelToTmxConfig.pathToNewTmx + tmxName;
    try (FileWriter tmxWriter = new FileWriter(tmxPath, false);
        FileReader bottomTmxReader = new FileReader(LevelToTmxConfig.pathToConfigBottom);
        FileReader topTmxReader = new FileReader(LevelToTmxConfig.pathToConfigTop)){
      topTmxReader.transferTo(tmxWriter);
      tmxWriter.write("\n");

      //matrix
      printTmxMatrix(level, tmxWriter);

      bottomTmxReader.transferTo(tmxWriter);
      tmxWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return tmxName;
  }

  private static void printTmxMatrix(Level level, FileWriter tmxWriter) throws IOException {
    JS0NHashMap js0NHashMap = new JS0NHashMap(LevelToTmxConfig.tilePallet);
    js0NHashMap.deserializeHashMap();
    for (int y = 0; y <= level.getHeight(); y++) {
      for (int x = 0; x <= level.getWidth(); x++) {
        if (level.map[x][y] == BlockDensity.WALL.density) {
          tmxWriter.write(js0NHashMap.get("WALL").toString());
        }
        else {
          tmxWriter.write(js0NHashMap.get("FLOOR").toString());
        }
        if (y == level.getHeight() && x == level.getWidth()) {
          continue;
        }
        tmxWriter.write(",");
      }
      tmxWriter.write("\n");
    }
  }

  private static String fileNameGenerator() {
    String name = UUID.randomUUID() + ".tmx";
    while (new File(LevelToTmxConfig.pathToNewTmx + name).isFile()) {
      name = UUID.randomUUID() + ".tmx";
    }
    return name;
  }
}
