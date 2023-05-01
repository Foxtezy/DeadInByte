package ru.nsu.fit.dib.projectdib.newMultiplayer;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;

public class ServerState {

  //список для инициализации клиента
  private final List<SpawnAction> spawnActionList = new CopyOnWriteArrayList<>();

  private final int mapSeed = new Random().nextInt();

  public List<SpawnAction> getSpawnActionList() {
    return spawnActionList;
  }

  public int getMapSeed() {
    return mapSeed;
  }

  public void addSpawnAction(SpawnAction spawnAction) {
    spawnActionList.add(spawnAction);
  }

}
