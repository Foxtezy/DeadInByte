package ru.nsu.fit.dib.projectdib.environment.loaderobjects;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.entity.Spawner;
import ru.nsu.fit.dib.projectdib.environment.mapperobjects.PhysicalObject;
import ru.nsu.fit.dib.projectdib.environment.mapperobjects.WallMapper;

/**
 * Класс отвечающий за загрузку и выгрузку чанков
 */
public class ChunkLoader {

  private final List<Chunk> loadedChunks = new LinkedList<>();


  private final Map<Chunk, List<PhysicalObject>> walls;

  private final Map<Chunk, List<Entity>> loadedWalls = new HashMap<>();

  private final int chunkSize;


  public ChunkLoader(WallMapper wallMapper) {
    walls = wallMapper.getWalls();
    chunkSize = wallMapper.getChunkSize();
  }

  public int getChunkSize() {
    return chunkSize;
  }

  public synchronized void updateChunks(Chunk currChunk) {

    List<Chunk> newLoadedChunks = currChunk.neighboringChunks();
    List<Chunk> copyLoadedChunks = new ArrayList<>(loadedChunks);
    List<Chunk> removeChunks = new ArrayList<>();
    List<Chunk> addChunks = new ArrayList<>();

    for (Chunk chunk : copyLoadedChunks) {
      if (!currChunk.isNeighboringChunk(chunk)) {
        removeChunks.add(chunk);
        loadedChunks.remove(chunk);
      }
    }

    for (Chunk chunk : newLoadedChunks) {
      if (!loadedChunks.contains(chunk)) {
        addChunks.add(chunk);
        loadedChunks.add(chunk);
      }
    }

    removeChunks.forEach(this::removeChunk);
    addChunks.forEach(this::addChunk);
  }

  private synchronized void addChunk(Chunk chunk) {
    if (!walls.containsKey(chunk)) {
      return;
    }
    loadedWalls.put(chunk, new ArrayList<>());
    List<PhysicalObject> objectsList = walls.get(chunk);
    for (PhysicalObject object : objectsList) {
      Entity e = Spawner.spawn(object.type().getName(),
          new SpawnData(object.x(), object.y()).put("width", object.width())
              .put("height", object.height()));
      loadedWalls.get(chunk).add(e);
    }
  }

  private void removeChunk(Chunk chunk) {
    if (!loadedWalls.containsKey(chunk)) {
      return;
    }
    List<Entity> entityList = loadedWalls.get(chunk);
    for (Entity entity : entityList) {
      FXGL.despawnWithDelay(entity, Duration.ZERO);
    }
  }

}
