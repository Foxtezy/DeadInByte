package ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules;

import javafx.scene.image.Image;
import ru.nsu.fit.dib.projectdib.data.ProjectConfig;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponSize;

public class TextureModule extends WeaponModule {


  public WeaponComponent component;
  protected int weaponID;
  protected WeaponSize weaponSize;
  private double imgRadius;

  private double angle;

  public TextureModule(int weaponID, WeaponSize weaponSize, double angle, double imgRadius) {
    this.weaponID = weaponID;
    this.weaponSize = weaponSize;
    this.angle = angle;
    this.imgRadius = imgRadius;
  }

  public Double getAngle() {
    return angle;
  }

  public WeaponComponent getComponent() {
    return component;
  }

  public void setComponent(WeaponComponent component) {
    this.component = component;
  }

  public int getWeaponID() {
    return weaponID;
  }


  public Image getTexturePath() {
    return switch (weaponSize) {
      case SMALL -> ProjectConfig._smallWeapon_img;
      case MEDIUM -> ProjectConfig._mediumWeapon_img;
      case BIG -> ProjectConfig._bigWeapon_img;
    };
  }

  public WeaponSize getWeaponSize() {
    return weaponSize;
  }

  public Integer getImageWidht() {
    return weaponSize.getWidth();
  }

  public int getImageHeight() {
    return weaponSize.getHeight();
  }

  public Double getImageRadius() {
    return imgRadius;
  }
}
