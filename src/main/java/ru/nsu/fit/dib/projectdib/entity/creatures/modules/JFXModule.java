package ru.nsu.fit.dib.projectdib.entity.creatures.modules;

import ru.nsu.fit.dib.projectdib.entity.components.PlayerComponent;

public class JFXModule extends CreatureModule{
  private final int imageID;            // ID внешности
  private PlayerComponent component;
  public JFXModule(int imageID){
    this.imageID=imageID;
  }
  public int getImageID() {
    return imageID;
  }
  public void setComponent(PlayerComponent component) {
    this.component = component;
  }
  public PlayerComponent getComponent() {
    return component;
  }
}
