package ru.nsu.fit.dib.projectdib.loaderobjects;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChunkLoader {

  private final List<Chunk> loadedChunks = new LinkedList<>();

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




  }


}
