package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import javafx.application.Platform;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

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
    //newEntity.spawn();
    Platform.runLater(newEntity::spawn);
  }
}
