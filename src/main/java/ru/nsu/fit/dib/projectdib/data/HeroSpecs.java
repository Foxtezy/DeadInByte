package ru.nsu.fit.dib.projectdib.data;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class HeroSpecs {
  private String name;
  private String mainWeapon;
  private String secondWeapon;
  private double speed = 250;
  private String playerImage;
  private List<Entity> inventory;

  private int HP;
  private double Str;
  private double Dex;
  private double Phys;
  private double Int;
  private double Wis;
  private double Char;

  public HeroSpecs(
      String name,
      String firstWeapon,
      String secondWeapon,
      int hp,
      Double speed,
      String playerImage) {
    this.name = name;
    this.mainWeapon = firstWeapon;
    this.secondWeapon = secondWeapon;
    this.HP = hp;
    this.speed = speed;
    this.playerImage = playerImage;
  }

  public String getPlayerImage() {
    return playerImage;
  }

  public Double getSpeed() {
    return speed;
  }

  public String getMainWeapon() {
    return mainWeapon;
  }

  public String getSecondWeapon() {
    return secondWeapon;
  }

  public int getHP() {
    return HP;
  }

  public double getStr() {
    return Str;
  }

  public double getDex() {
    return Dex;
  }

  public double getPhys() {
    return Phys;
  }

  public double getInt() {
    return Int;
  }

  public double getWis() {
    return Wis;
  }

  public double getChar() {
    return Char;
  }

  public void setMainWeapon(String newWeapon) {
    mainWeapon = newWeapon;
  }

  public void setSecondWeapon(String newWeapon) {
    secondWeapon = newWeapon;
  }
}
