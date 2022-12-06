package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

public class Spawn {

  public static void spawnInitialObjects() {
    Entity closedDoor = spawn("closedDoor", 256, 928);
    Entity openedDoor = getGameWorld().create("openedDoor", new SpawnData(256, 928));
    Entity doorTrigger =
        spawn("doorTrigger", new SpawnData(272, 880).put("openedDoor", openedDoor));
    Entity button = spawn("button", new SpawnData(64, 1080).put("closedDoor", closedDoor));

    Entity closedDoor2 = spawn("closedDoor", 384, 1280);
    Entity openedDoor2 = getGameWorld().create("openedDoor", new SpawnData(384, 1280));
    Entity button2 = spawn("button", new SpawnData(384, 1220).put("closedDoor", closedDoor2));
    Entity enemyTrigger = spawn("enemyTrigger", 240, 192);
  }

  public static void enemySpawn() {
    spawn("enemy",48, 48);
    //spawn("enemy", 208, 64);
    //spawn("enemy", 288, 96);
    //spawn("enemy", 400, 48);
    //spawn("enemy", 48, 144);
    //spawn("enemy", 320, 96);
   // spawn("enemy", 400, 160);
  }
}
