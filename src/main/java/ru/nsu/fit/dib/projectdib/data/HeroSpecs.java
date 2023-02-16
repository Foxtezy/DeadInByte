package ru.nsu.fit.dib.projectdib.data;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class HeroSpecs {
  private String name;
  private String mainWeapon;
  private String secondWeapon;
  private double speed = 250;
  private String playerImage;
  private List<Entity> inventory;

  private int healthPoints;
  private double strength;
  private double dexterity;
  private double physique;
  private double intelligence;
  private double wisdom;
  private double charisma;

  public HeroSpecs(
      String name,
      String firstWeapon,
      String secondWeapon,
      int hp,
      Double speed,
      String playerImage,
      double strength,
      double dexterity,
      double physique,
      double intelligence,
      double wisdom,
      double charisma) {
    this.name = name;
    this.mainWeapon = firstWeapon;
    this.secondWeapon = secondWeapon;
    this.healthPoints = hp;
    this.speed = speed;
    this.playerImage = playerImage;
    this.strength = strength;
    this.dexterity = dexterity;
    this.physique = physique;
    this.intelligence = intelligence;
    this.wisdom = wisdom;
    this.charisma = charisma;
  }

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
    this.healthPoints = hp;
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

  public int getHealthPoints() {
    return healthPoints;
  }

  public double getStrength() {
    return strength;
  }

  public double getDexterity() {
    return dexterity;
  }

  public double getPhysique() {
    return physique;
  }

  public double getIntelligence() {
    return intelligence;
  }

  public double getWisdom() {
    return wisdom;
  }

  public double getCharisma() {
    return charisma;
  }

  public void setHealthPoints(int healthPoints) {
    this.healthPoints = healthPoints;
  }

  public void setStrength(double strength) {
    this.strength = strength;
  }

  public void setDexterity(double dexterity) {
    this.dexterity = dexterity;
  }

  public void setPhysique(double physique) {
    this.physique = physique;
  }

  public void setIntelligence(double intelligence) {
    this.intelligence = intelligence;
  }

  public void setWisdom(double wisdom) {
    this.wisdom = wisdom;
  }

  public void setCharisma(double charisma) {
    this.charisma = charisma;
  }

  public List<Entity> getInventory() {
    return new ArrayList<>(inventory);
  }

  public void setMainWeapon(String newWeapon) {
    mainWeapon = newWeapon;
  }

  public void setSecondWeapon(String newWeapon) {
    secondWeapon = newWeapon;
  }
}
