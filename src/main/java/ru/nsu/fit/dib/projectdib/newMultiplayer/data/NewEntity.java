package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import com.almasb.fxgl.entity.SpawnData;

public class NewEntity {

  private Integer id;
  private final String name;
  private final SpawnData spawnData;

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public SpawnData getSpawnData() {
    return spawnData;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public NewEntity(Integer id, String name, SpawnData spawnData) {
    this.id = id;
    this.name = name;
    this.spawnData = spawnData;
  }
}
