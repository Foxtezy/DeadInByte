package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.MAX_PLAYERS;

import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionStatus;

public abstract class GameAction {

  //ID действия в формате:
  //id создателя:id действия
  //"1:132"
  private final int id;
  private   volatile ActionStatus status;
  public static Integer clientID = 1;
  //TODO: перенести поле c ID клиента в нужное место
  public static Integer actionNumbers = 0;
  //TODO: перенести счетчик действий в нужное место
  public GameAction(){
    id=clientID+MAX_PLAYERS*(actionNumbers++);
    status=ActionStatus.CREATED;
  }
  public GameAction(int id, ActionStatus status){
    this.id=id;
    this.status=status;
  }
  public abstract void run();

  public ActionStatus getStatus() {
    return status;
  }

  public synchronized void setStatus(ActionStatus status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public int getOwnerId() {
    return id%MAX_PLAYERS;
  }
}
