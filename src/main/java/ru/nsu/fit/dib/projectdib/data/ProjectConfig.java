package ru.nsu.fit.dib.projectdib.data;

/**
 * Настройки игры, не изменяемые пользователем.
 */
public interface ProjectConfig {
  //Config
  String _cfg = "src/main/resources/cfg.ini";
  //PlayerConfig
  String _player = "assets/textures/HeroesMovementSpriteSheet.png";
  int _player_numberColumns = 13;
  int _player_width = 160;
  int _player_height = 320;


  //UIConfig
  String _fontDustyPro = "assets/UI/fonts/DustyPro.ttf";
  String _forestAnimationUI = "assets/UI/elements/forest1k.png";
  String _campfireAnimationUI = "assets/UI/elements/campfire1k.png";
  String _menuButton = "assets/UI/elements/menu_button1k.png";
  String _menuSelectedButton = "assets/UI/elements/menu_selected_button1k.png";
  String _returnButton = "assets/UI/elements/return_button1k.png";
  String _returnSelectedButton = "assets/UI/elements/return_selected_button1k.png";
  String _loadingAnimation = "assets/UI/elements/loadingAnimation.gif";
  String style = "assets/UI/elements/style.css";
}
