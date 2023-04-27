package ru.nsu.fit.dib.projectdib.entity.creatures.modules;

import com.almasb.fxgl.entity.component.Component;
import ru.nsu.fit.dib.projectdib.entity.components.CreatureComponent;
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
  public CreatureComponent getComponent() {
    // division for enemies and heroes. One of them have to be equal null.
      if(this.component == null && this.enemyComponent == null){
        throw new NullPointerException("NLP in JFXModule.getComponent()");
      }
    return this.component != null ? this.component : this.enemyComponent;
  }
  public Boolean hasHeroComponent(){
    if(this.component == null && this.enemyComponent == null){
      throw new NullPointerException("NLP in JFXModule.hasHeroComponent()");
    }
    return this.component != null;
  }

  public EnemyComponent getEnemyComponent(){return enemyComponent;}
}
