package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.TakeWeaponAction;

public class ActionQueues {
  private final BlockingQueue<SpawnAction> newSpawnActions;
  private final BlockingQueue<TakeWeaponAction> newTakeWeaponActions;

  public ActionQueues() {
    newSpawnActions=new LinkedBlockingQueue<>();
    newTakeWeaponActions = new LinkedBlockingQueue<>();
  }

  public BlockingQueue<SpawnAction> getNewSpawnActions() {
    return newSpawnActions;
  }

  public void addToActionPacket(ActionPacket actionPacket){
    newSpawnActions.forEach(action -> actionPacket.getSpawnActions().put(action.getId(),action));
  }


}
