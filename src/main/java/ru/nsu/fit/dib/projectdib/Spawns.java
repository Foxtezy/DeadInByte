package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;


public class Spawns {

  public static Entity closedDoor_144_192;

  public static void spawnInitialObjects() {
    closedDoor_144_192 = spawn("closedDoor", 144, 192);
  }

}
