package ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules;

public class TimingsModule extends WeaponModule {

  private double impactSpeed;
  private double rollbackTime;

  public TimingsModule(int rollbackTime, int impactSpeed) {
    super();
    this.rollbackTime = rollbackTime;
    this.impactSpeed = impactSpeed;
  }

  public double getImpactSpeed() {
    return impactSpeed;
  }

  public double getRollbackTime() {
    return rollbackTime;
  }

  public void setRollbackTime(int rollbackTime) {
    this.rollbackTime = rollbackTime;
  }

  public double getIterationTime(){
    return rollbackTime+impactSpeed;
  }
}
