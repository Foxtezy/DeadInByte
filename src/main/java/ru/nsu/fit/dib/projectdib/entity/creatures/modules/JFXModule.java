package ru.nsu.fit.dib.projectdib.entity.creatures.modules;

import ru.nsu.fit.dib.projectdib.entity.components.EnemyComponent;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;

public class JFXModule extends CreatureModule{
  private final int imageID;            // ID внешности
  private HeroComponent component;
  private EnemyComponent enemyComponent;
  public JFXModule(int imageID){
    this.imageID=imageID;
  }
  public int getImageID() {
    return imageID;
  }
  public void setComponent(HeroComponent component) {
    this.component = component;
  }
  public void setEnemyComponent(EnemyComponent component){
    this.enemyComponent = component;
  }
  public HeroComponent getComponent() {
    return component;
  }
  public EnemyComponent getEnemyComponent(){return enemyComponent;}
}
