package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import com.almasb.fxgl.entity.Entity;
import ru.nsu.fit.dib.projectdib.entity.components.WeaponComponent;
import ru.nsu.fit.dib.projectdib.entity.components.fight.WeaponInventoryComponent;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class WeaponAction extends GameAction{
  private final int heroId;
  private final int weaponId;
  private final WeaponActionType type;

  @Override
  public String toString() {
    return "WeaponAction{" +
        "heroId=" + heroId +
        ", weaponId=" + weaponId +
        ", type=" + type +
        '}';
  }

  public WeaponAction(WeaponActionType type,int heroId,int weaponId){
    super();
    this.type=type;
    this.heroId=heroId;
    this.weaponId=weaponId;
  }
  @Override
  public void run() {
    System.out.println(this);
    switch (type){
      case THROW -> {
        Entity hero = MCClient.getClientState().getIdHashTable().get(heroId);
        Entity weapon = MCClient.getClientState().getIdHashTable().get(weaponId);
        if (hero==null) throw new IllegalArgumentException("Hero with id "+heroId+" not found");
        if (weapon==null) throw new IllegalArgumentException("Weapon with id "+weaponId+" not found");
        if (hero.getComponent(WeaponInventoryComponent.class).getActiveWeapon()==weapon) {
          hero.getComponent(WeaponInventoryComponent.class).throwWeapon();
        }
        else {
          int k=0;
          while(hero.getComponent(WeaponInventoryComponent.class).getActiveWeapon()!=weapon
              && k<=hero.getComponent(WeaponInventoryComponent.class).size()){
            k++;
            hero.getComponent(WeaponInventoryComponent.class).swapWeapon();
          }
          hero.getComponent(WeaponInventoryComponent.class).throwWeapon();
        }
      }
      case TAKE -> {    Entity hero = MCClient.getClientState().getIdHashTable().get(heroId);
        if (hero==null) throw new IllegalArgumentException("Hero with id "+heroId+" not found");
        Entity weapon = MCClient.getClientState().getIdHashTable().get(weaponId);
        if (weapon==null) throw new IllegalArgumentException("Weapon with id "+weaponId+" not found");
        hero.getComponent(WeaponInventoryComponent.class).takeWeapon(weapon);}
    }
  }

  public WeaponActionType getAction() {
    return type;
  }

  public int getUser() {
    return heroId;
  }

  public int getWeapon() {
    return weaponId;
  }

  public enum WeaponActionType {
    THROW(1),
    TAKE(0);
    private final int id;
    WeaponActionType(int id) {
      this.id =id;
    }
    public static WeaponActionType getByID(int id){
      if (id==0) return TAKE;
      else return THROW;
    }

    public int getID() {
      return id;
    }
  }

  public int getHeroId() {
    return heroId;
  }

  public int getWeaponId() {
    return weaponId;
  }

  public WeaponActionType getType() {
    return type;
  }
}
