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
  Image heroes_image = new Image(_player);
  String _enemy = "assets/textures/Sprite-0001-export.png";
  int lengthOfCell = 160; //TODO
  Image enemies_image = new Image(_enemy);
  int _enemy_numberColumns = 8;
  double rangeOfVision = lengthOfCell * 8;
  int _enemy_width = 160;
  int _enemy_height = 320;
  int _player_numberColumns = 13;
  int _player_width = 160;
  int _player_height = 320;

  //UIConfig
  String _fontDustyPro = "assets/ui/fonts/DustyPro.ttf";
  String _fontAgencyFB = "assets/ui/fonts/agency-fb.ttf";
  String _forestAnimationUI = "assets/ui/elements/forest1k.png";
  String _campfireAnimationUI = "assets/ui/elements/campfire1k.png";
  String _menuButton = "assets/ui/elements/menu_button1k.png";
  String _menuSelectedButton = "assets/ui/elements/menu_selected_button1k.png";
  String _returnButton = "assets/ui/elements/return_button1k.png";
  String _returnSelectedButton = "assets/ui/elements/return_selected_button1k.png";
  String _loadingAnimation = "assets/ui/elements/loadingAnimation.gif";
  String style = "assets/ui/elements/style.css";
  //Weapon
  String _smallWeapon = "assets/textures/SmallWeapon.png";
  String _mediumWeapon = "assets/textures/MediumWeapon.png";
  String _bigWeapon = "assets/textures/BigWeapon.png";
  String _projectile_path = "assets/textures/projectile.png";
  String _character_file = "src/main/resources/saves/character.json";
  Image _projectile_img = new Image(_projectile_path);
  //================================================================================================
  String CONTROLS_PATH = "src/main/resources/config/controls.json";
  int _WEAPON_COLUMNS =16;
  Image _smallWeapon_img = new Image(_smallWeapon);
  Image _mediumWeapon_img = new Image(_mediumWeapon);
  Image _bigWeapon_img = new Image(_bigWeapon);
  Integer MAX_PLAYERS = 4;
}
