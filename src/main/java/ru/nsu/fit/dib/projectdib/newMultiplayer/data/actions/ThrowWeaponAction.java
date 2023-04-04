package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import com.almasb.fxgl.entity.Entity;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class ThrowWeaponAction extends GameAction{
  private final Integer heroId;
  public ThrowWeaponAction(Integer heroId){
    super();
    this.heroId=heroId;
  }
  @Override
  public void run() {
    Entity hero = MCClient.getClientState().getIdHashTable().get(heroId);
    if (hero==null) throw new IllegalArgumentException("Hero with id "+heroId+" not found");
    hero.getComponent(HeroComponent.class).throwWeapon();
  }
}
