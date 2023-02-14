package ru.nsu.fit.dib.projectdib.UI;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.IntroScene;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;

public class ScenesFactory extends SceneFactory {

  @Override
  public FXGLMenu newMainMenu() {
    return new MainMenu(MenuType.MAIN_MENU);
  }

  @Override
  public FXGLMenu newGameMenu() {
    return new PauseMenu(MenuType.GAME_MENU);
  }

  @Override
  public IntroScene newIntro(){
    return new IntroMenu();
  }

}
