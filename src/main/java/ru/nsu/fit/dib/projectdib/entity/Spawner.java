package ru.nsu.fit.dib.projectdib.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

/**
 * Обёртка над FXGL.spawn() (EntitySpawner это другое (мультиплейер))
 * Нужно для решения многопоточных проблем.
 */
public class Spawner {

  private static final Object monitor = new Object();

  private Spawner() {
    throw new UnsupportedOperationException("utility class");
  }

  public static Entity spawn(String type, SpawnData spawnData) {
    synchronized (monitor) {
      return FXGL.spawn(type, spawnData);
    }
  }
}
