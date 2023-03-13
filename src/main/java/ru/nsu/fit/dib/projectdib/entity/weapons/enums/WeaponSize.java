package ru.nsu.fit.dib.projectdib.entity.weapons.enums;

public enum WeaponSize {
  SMALL(160, 160),
  MEDIUM(160, 320),
  BIG(160, 480);

  int width;
  int height;

  WeaponSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}