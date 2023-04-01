package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.newentities.NewEntity;

public class SpawnAction extends GameAction {
  private final NewEntity newEntity;
  public SpawnAction(NewEntity newEntity){
    super();
    this.newEntity=newEntity;
  }
  @Override
  public void run() {
    newEntity.spawn();
  }
}
