package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

/**
 * Класс спавна... чего? Я так понимаю это что-то нужно было для тестирования?
 * <p>
 * Прописать что это.
 */
public class Spawn {

  public static void spawnInitialObjects() {
    Entity closedDoor = spawn("closedDoor", 144, 192);
    Entity openedDoor = getGameWorld().create("openedDoor", new SpawnData(144, 192));
    Entity doorTrigger = spawn("doorTrigger", new SpawnData(144, 144)
        .put("openedDoor", openedDoor));
    Entity button = spawn("button", new SpawnData(144, 256)
        .put("closedDoor", closedDoor));
    closedDoor = spawn("closedDoor", 240, 80);
    openedDoor = getGameWorld().create("openedDoor", new SpawnData(240, 80));
    doorTrigger = spawn("doorTrigger", new SpawnData(240, 16)
        .put("openedDoor", openedDoor));
    button = spawn("button", new SpawnData(240, 144)
        .put("closedDoor", closedDoor));
  }

}
