package ru.nsu.fit.dib.projectdib.entity.weapons.enums.modules;

public class TimingsModule extends WeaponModule {

  protected int impactSpeed;
  protected int rollbackTime;

  public TimingsModule(int rollbackTime, int impactSpeed) {
    super();
    this.rollbackTime = rollbackTime;
    this.impactSpeed = impactSpeed;
  }

  public int getImpactSpeed() {
    return impactSpeed;
  }

  protected int getRollbackTime() {
    return rollbackTime;
  }

  protected void setRollbackTime(int rollbackTime) {
    this.rollbackTime = rollbackTime;
  }
}
