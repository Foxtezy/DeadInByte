package ru.nsu.fit.dib.projectdib.environment.tmxbuilder;

/**
 * Настройки класса LevelToTMX
 */
public interface LevelToTmxConfig {

  String pathToConfigTop = "src/main/resources/tmx_configs/tmx_config_top.txt";

  String pathToConfigBottom = "src/main/resources/tmx_configs/tmx_config_bottom.txt";

  String pathToNewTmx = "levels/";

  String tilePallet = "src/main/resources/assets/levels/texture_pallettes/new_palette.json";
}
