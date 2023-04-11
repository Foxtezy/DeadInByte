package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import static java.util.concurrent.CompletableFuture.runAsync;

import java.util.Map;
import java.util.stream.Stream;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.TakeWeaponAction;

public class ActionPacket {

  public synchronized Map<Integer, SpawnAction> getSpawnActions() {
    return spawnActions;
  }

  private final Map<Integer, SpawnAction> spawnActions;

  public synchronized Map<Integer, TakeWeaponAction> getTakeWeaponActions() {
    return takeWeaponActions;
  }

  private final Map<Integer, TakeWeaponAction> takeWeaponActions;

  public ActionPacket(Map<Integer, SpawnAction> spawnActions,
      Map<Integer, TakeWeaponAction> takeWeaponActions) {
    this.spawnActions = spawnActions;
    this.takeWeaponActions = takeWeaponActions;
  }

  /**
   * Updates this packet by someone.
   *
   * @param actionPacket
   */
  public void update(ActionPacket actionPacket) {

    actionPacket.getSpawnActions().forEach((id, action) -> {
      //если APPROVED действия нет в базе - добавляем(создан другим клиентом или сервером)
      if (getSpawnActions().get(id) == null) {
        getSpawnActions().put(id, action);
      }
      //если APPROVED действия есть в базе - меняем статус на APPROVE
      // (создан этим клиентом или уже получил информацию о действии)
      // и меняем ID
      else if (getSpawnActions().get(id) != null
          && getSpawnActions().get(id).getStatus() == ActionStatus.CREATED) {
        getSpawnActions().get(id).setStatus(action.getStatus());
        getSpawnActions().get(id).getNewEntity().setId(action.getNewEntity().getID());
        getSpawnActions().get(id).getNewEntity().setWeaponId(action.getNewEntity().getWeaponId());
      }
    });
    deleteCompletedActions(getSpawnActions(), actionPacket.getSpawnActions());

    actionPacket.getTakeWeaponActions().forEach((id, action) -> {
      //если APPROVED действия нет в базе - добавляем(создан другим клиентом или сервером)
      if (getTakeWeaponActions().get(id) == null) {
        getTakeWeaponActions().put(id, action);
      }
      //если APPROVED действия есть в базе - меняем статус на APPROVE
      // (создан этим клиентом или уже получил информацию о действии)
      // и меняем ID
      else if (getTakeWeaponActions().get(id) != null
          && getTakeWeaponActions().get(id).getStatus() == ActionStatus.CREATED) {
        getTakeWeaponActions().get(id).setStatus(action.getStatus());
      }
    });
    deleteCompletedActions(getTakeWeaponActions(), actionPacket.getTakeWeaponActions());
  }

  private void deleteCompletedActions(Map<Integer, ? extends GameAction> lastActions,
      Map<Integer, ? extends GameAction> currActions) {
    Stream<Integer> keys = lastActions.keySet().stream()
        .filter(key -> lastActions.get(key).getStatus() == ActionStatus.COMPLETED).filter(
            currActions::containsKey);
    //runAsync(() -> keys.forEach(lastActions::remove));
  }
}