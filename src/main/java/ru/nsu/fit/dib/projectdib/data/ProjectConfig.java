package ru.nsu.fit.dib.projectdib.data;

/**
 * Настройки игры, не изменяемые пользователем.
 */
public interface ProjectConfig {

  String _player = "assets/textures/player.png";
  String _cfg = "src/main/resources/cfg.ini";


  //UIConfig
  String _fontDustyPro = "file:/F:/DeadInByte/src/main/resources/assets/UI/fonts/DustyPro.ttf";
  String _forestAnimationUI = "assets/UI/elements/forest1k.png";
  String _campfireAnimationUI = "assets/UI/elements/campfire1k.png";
  String _menuButton = "assets/UI/elements/menu_button1k.png";
  String _menuSelectedButton = "assets/UI/elements/menu_selected_button1k.png";
  String _returnButton = "assets/UI/elements/return_button1k.png";
  String _returnSelectedButton = "assets/UI/elements/return_selected_button1k.png";

}
