package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import javafx.application.Platform;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionStatus;

public class SpawnAction extends GameAction {

  public NewEntity getNewEntity() {
    return newEntity;
  }

  private final NewEntity newEntity;
  public SpawnAction(NewEntity newEntity){
    super();
    this.newEntity=newEntity;
  }
  public SpawnAction(ActionStatus status, int id, NewEntity newEntity){
    super(id,status);
    this.newEntity=newEntity;
  }
  @Override
  public void run() {
    Platform.runLater(newEntity::spawn);
  }
}
