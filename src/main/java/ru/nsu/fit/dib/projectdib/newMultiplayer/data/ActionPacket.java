package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import java.util.Map;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.TakeWeaponAction;

public class ActionPacket {

  public Map<String, SpawnAction> getSpawnActions() {
    return spawnActions;
  }

  private final Map<String,SpawnAction> spawnActions;
  private final Map<String,TakeWeaponAction> takeWeaponActions;
  public ActionPacket(Map<String,SpawnAction> spawnActions,Map<String,TakeWeaponAction> takeWeaponActions) {
    this.spawnActions=spawnActions;
    this.takeWeaponActions=takeWeaponActions;
  }

  /**
   * Updates this packet by someone.
   * @param actionPacket
   */
  public void update(ActionPacket actionPacket){

    actionPacket.getSpawnActions().forEach((id,action) -> {
      //если APPROVED действия нет в базе - добавляем(создан другим клиентом или сервером)
      if (spawnActions.get(id)==null) spawnActions.put(id,action);
      //если APPROVED действия есть в базе - меняем статус на APPROVE
      // (создан этим клиентом или уже получил информацию о действии)
      // и меняем ID
      else if (spawnActions.get(id)!=null && spawnActions.get(id).getStatus() == ActionStatus.CREATED) {
        spawnActions.get(id).setStatus(action.getStatus());
        spawnActions.get(id).getNewEntity().setId(action.getNewEntity().getID());
        spawnActions.get(id).getNewEntity().setWeaponId(action.getNewEntity().getWeaponId());
      }
    });
  }
}
