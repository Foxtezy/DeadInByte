package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import javafx.application.Platform;

public class SpawnAction extends GameAction {

  public NewEntity getNewEntity() {
    return newEntity;
  }

  private final NewEntity newEntity;

  public SpawnAction(NewEntity newEntity){
    this.newEntity=newEntity;
  }
  @Override
  public void run() {
    Platform.runLater(newEntity::spawn);
  }
}
