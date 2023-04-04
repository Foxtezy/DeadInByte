package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import java.util.List;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionStatus;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.TypeAction;

public abstract class GameAction {
  //ID действия в формате:
  //id создателя:id действия
  //"1:132"
  private final String id;
  private volatile ActionStatus status;
  public static Integer clientID = 1;
  //TODO: перенести поле c ID клиента в нужное место
  public static Integer actionNumbers = 0;
  //TODO: перенести счетчик действий в нужное место
  public GameAction(){
    id=clientID.toString()+":"+ (actionNumbers++).toString();
    status=ActionStatus.CREATED;
  }

  public abstract void run();

  public ActionStatus getStatus() {
    return status;
  }

  public synchronized void setStatus(ActionStatus status) {
    this.status = status;
  }

  public String getId() {
    return id;
  }
}
