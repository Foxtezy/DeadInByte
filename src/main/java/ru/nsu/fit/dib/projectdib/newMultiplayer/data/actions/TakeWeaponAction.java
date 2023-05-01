package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import com.almasb.fxgl.entity.Entity;
import ru.nsu.fit.dib.projectdib.entity.components.HeroComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.WeaponInventoryComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class TakeWeaponAction extends GameAction{
  private final Integer heroId;

  public Integer getWeaponId() {
    return weaponId;
  }

  private final Integer weaponId;
  public TakeWeaponAction(Integer heroId,Integer weaponId){
    super();
    this.heroId=heroId;
    this.weaponId=weaponId;
  }
  @Override
  public void run() {

  }
}
