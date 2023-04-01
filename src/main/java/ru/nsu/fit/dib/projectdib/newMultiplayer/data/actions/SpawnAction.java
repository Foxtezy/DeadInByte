package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

public class SpawnAction extends GameAction {

  public NewEntity getNewEntity() {
    return newEntity;
  }

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
