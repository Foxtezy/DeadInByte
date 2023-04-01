package ru.nsu.fit.dib.projectdib.newMultiplayer.data;


import java.util.List;

public class GameStatePacket {

  private final List<EntityState> entitiesStates;
  private final ActionPacket actions;
  public List<EntityState> getEntitiesStates() {
    return entitiesStates;
  }

  public GameStatePacket(ActionPacket actions,
      List<EntityState> entitiesStates) {
    this.entitiesStates = entitiesStates;
    this.actions=actions;
  }

  public ActionPacket getActions() {
    return actions;
  }
}
