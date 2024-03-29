package ru.nsu.fit.dib.projectdib.environment.loaderobjects;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

/**
 * Компонент который нужно добавить к сущьности, которая будет прогружать чанки
 * (в нашем случае это player)
 */
public class ChunkLoaderComponent extends Component {

  private final ChunkLoader chunkLoader;
  private Chunk currentChunk;

  private final int chunkSize;

  public ChunkLoaderComponent(ChunkLoader chunkLoader) {
    this.chunkLoader = chunkLoader;
    this.chunkSize = chunkLoader.getChunkSize();
  }

  @Override
  public void onAdded() {
    Point2D position = entity.getPosition();
    currentChunk = new Chunk((int) position.getX() / chunkSize, (int) position.getY() / chunkSize);
    chunkLoader.updateChunks(currentChunk);
  }

  @Override
  public void onUpdate(double tpf) {
    Point2D position = entity.getPosition();
    if (currentChunk.getX() == (int) position.getX() / chunkSize &&
        currentChunk.getY() == (int) position.getY() / chunkSize) {
      return;
    }
    currentChunk = new Chunk((int) position.getX() / chunkSize, (int) position.getY() / chunkSize);
    chunkLoader.updateChunks(currentChunk);
  }
}
