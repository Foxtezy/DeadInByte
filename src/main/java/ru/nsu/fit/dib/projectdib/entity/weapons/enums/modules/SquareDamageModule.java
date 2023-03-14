package ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules;

public class SquareDamageModule extends WeaponModule {

  private double distance;
  private double radius;

  public SquareDamageModule(double distance, double radius) {
    this.distance = distance;
    this.radius = radius;
  }

  public double getDistance() {
    return distance;
  }

  protected void setDistance(double distance) {
    this.distance = distance;
  }

  public double getRadius() {
    return radius;
  }

  protected void setRadius(double radius) {
    this.radius = radius;
  }

}
