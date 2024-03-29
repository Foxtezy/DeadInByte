package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGLForKtKt.onKey;
import static com.almasb.fxgl.dsl.FXGLForKtKt.onKeyUp;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.CONTROLS_PATH;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._cfg;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameSettings;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import ru.nsu.fit.dib.projectdib.data.Config;
import ru.nsu.fit.dib.projectdib.data.Controls;
import ru.nsu.fit.dib.projectdib.data.JSONController;
import ru.nsu.fit.dib.projectdib.ui.ScenesFactory;

/**
 * Загружает пользовательские и остальные настройки игры.
 */
public class SettingsLoader {
  private final GameSettings settings;

  /**
   * Коструктор загрузчика. Требует FXGLSettings.
   *
   * @param settings - настройки игры.
   */
  public SettingsLoader(GameSettings settings) {
    this.settings = settings;
  }

  /**
   * Загружает настройки.
   */
  public void run() {
    settings.setScaleAffectedOnResize(true);
    settings.setTitle("Dead In Byte: The Last Dungeon");
    settings.setVersion("1.0");
    settings.setAppIcon("dibicon.png");
    settings.setManualResizeEnabled(true);
    settings.setPreserveResizeRatio(true);
    settings.setSceneFactory(new ScenesFactory());
    settings.setMainMenuEnabled(true);
    settings.setDeveloperMenuEnabled(true);
    settings.setApplicationMode(ApplicationMode.DEVELOPER);
    Config.setConfig(_cfg);
    JSONController.<Controls>load(CONTROLS_PATH,Controls.class);
    // Window mod
    switch (Config.WINDOW_MODE) {
      case "Window" -> {
        settings.setWidth(Config.WINDOW_WIDTH);
        settings.setHeight(Config.WINDOW_HEIGHT);
      }
      case "Borderless" -> {
        System.out.print(Config.WINDOW_MODE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        settings.setHeight(dimension.height);
        settings.setWidth(dimension.width);
      }
      case "Full-screen" -> {
        settings.setWidth(Config.WINDOW_WIDTH);
        settings.setHeight(Config.WINDOW_HEIGHT);
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
      }
      default -> {
        settings.setWidth(600);
        settings.setHeight(600);
      }
    }
  }

}
