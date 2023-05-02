package ru.nsu.fit.dib.projectdib.entity.weapons.enums;

import javafx.scene.image.Image;
import ru.nsu.fit.dib.projectdib.data.ProjectConfig;

public enum WeaponSize {
  SMALL(160, 160, ProjectConfig._smallWeapon_img),
  MEDIUM(160, 320,ProjectConfig._mediumWeapon_img),
  BIG(160, 480,ProjectConfig._bigWeapon_img);

  private final int width;
  private final int height;
  private final Image image;
  WeaponSize(int width, int height, Image image) {
    this.width = width;
    this.height = height;
    this.image=image;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Image getImage() {
    return image;
  }
}