package ru.nsu.fit.dib.projectdib.multiplayer.data;


import com.almasb.fxgl.app.GameSettings;
import java.util.List;

public class GameStatePacket {
  private final List<NewEntity> newEntityList;
  private final List<EntityState> allCoordinatesOfEntitiesList;

  public List<NewEntity> getNewEntityList() {
    return newEntityList;
  }

  public List<EntityState> getAllCoordinatesOfEntitiesList() {
    return allCoordinatesOfEntitiesList;
  }

  public GameStatePacket(List<NewEntity> newEntityList,List<EntityState> allCoordinatesOfEntitiesList) {
    this.newEntityList=newEntityList;
    this.allCoordinatesOfEntitiesList=allCoordinatesOfEntitiesList;
  }
}
