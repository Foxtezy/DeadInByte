package ru.nsu.fit.dib.projectdib.loaderobjects;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.util.Duration;
import ru.nsu.fit.dib.projectdib.mapperobjects.GameObject;

public class ChunkLoader {

  private final List<Chunk> loadedChunks = new LinkedList<>();


  private final Map<Chunk, List<GameObject>> walls;

  private final Map<Chunk, List<Entity>> loadedWalls = new HashMap<>();

  public ChunkLoader(Map<Chunk, List<GameObject>> walls) {
    this.walls = walls;
  }

  public void updateChunks(Chunk currChunk) {

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

  private void addChunk(Chunk chunk) {
    if (!walls.containsKey(chunk)) {
      return;
    }
    loadedWalls.put(chunk, new ArrayList<>());
    List<GameObject> objectsList = walls.get(chunk);
    for (GameObject object : objectsList) {
      Entity e = FXGL.spawn(object.type().getName(),
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
