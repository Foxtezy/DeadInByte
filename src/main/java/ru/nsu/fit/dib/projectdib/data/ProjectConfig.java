package ru.nsu.fit.dib.projectdib.data;

import javafx.scene.image.Image;

/**
 * Настройки игры, не изменяемые пользователем.
 */
public interface ProjectConfig {
  //Config
  String _cfg = "src/main/resources/cfg.ini";
  //PlayerConfig
  String _player = "assets/textures/HeroesMovementSpriteSheet.png";
  String _enemy = "assets/textures/Sprite-0001-export.png";
  int lengthOfCell = 80; //TODO
  int _enemy_numberColumns = 8;
  int _enemy_width = 160;
  int _enemy_height = 320;
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
  Integer SERVER_PORT = 8080;
  //Weapon
  String _smallWeapon = "assets/textures/SmallWeapon.png";
  String _mediumWeapon = "assets/textures/MediumWeapon.png";
  String _bigWeapon = "assets/textures/BigWeapon.png";
  String _projectile_path = "assets/textures/projectile.png";
  Image _projectile_img = new Image(_projectile_path);
  //================================================================================================
  String CONTROLS_PATH = "src/main/resources/config/controls.json";
  int _WEAPON_COLUMNS =16;
  Image _smallWeapon_img = new Image(_smallWeapon);
  Image _mediumWeapon_img = new Image(_mediumWeapon);
  Image _bigWeapon_img = new Image(_bigWeapon);
  Integer MAX_PLAYERS = 4;
}
