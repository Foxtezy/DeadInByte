package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

public class Spawn {

  public static void spawnInitialObjects() {
    Entity closedDoor = spawn("closedDoor", 240, 912);
    Entity openedDoor = getGameWorld().create("openedDoor", new SpawnData(240, 912));
    Entity doorTrigger =
        spawn("doorTrigger", new SpawnData(240, 864).put("openedDoor", openedDoor));
    Entity button = spawn("button", new SpawnData(64, 1104).put("closedDoor", closedDoor));

    Entity closedDoor2 = spawn("closedDoor", 368, 1264);
   getGameWorld().create("openedDoor", new SpawnData(368, 1264));
    spawn("button", new SpawnData(384, 1232).put("closedDoor", closedDoor2));
    spawn("enemyTrigger", 240, 192);
    getGameWorld().create("enemy", new SpawnData(48, 48));
    //getGameWorld().create("enemy", new SpawnData(208, 64));
    //getGameWorld().create("enemy", new SpawnData(288, 96));
    getGameWorld().create("enemy", new SpawnData(400, 48));
    //getGameWorld().create("enemy", new SpawnData(48, 144));
  }

  public static void enemySpawn() {
    spawn("enemy",48, 48);
    //spawn("enemy", 208, 64);
    //spawn("enemy", 288, 96);
    spawn("enemy", 400, 48);
    //spawn("enemy", 48, 144);
    //spawn("enemy", 320, 96);
   // spawn("enemy", 400, 160);
  }
}
