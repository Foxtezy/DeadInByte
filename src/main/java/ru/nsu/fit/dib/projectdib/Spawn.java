package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;


public class Spawn {

  public static Entity button;
  //public static SpawnData spawnData;
  public static Entity door;

  //public static Entity button;
  public static void spawnInitialObjects() {
    //Map<Integer, Entity> doors = new HashMap<>();
    //doors.put(1, spawn("closedDoor", 144, 192));
    //Map<Integer, Entity > buttons = new HashMap<>();
    //buttons.put(1, spawn("button", 144, 256));
    door = spawn("closedDoor", 144, 192);
    //data = null;
    //data.put("closedDoor", door);
    //SpawnData spawnData = new SpawnData();
    //spawnData.put("door", door);
    button = spawn("button", 144, 256);
    door = getGameWorld().create("closedDoor", new SpawnData(144, 192).put("door", "E"));
    door = spawn("closedDoor", 32, 96);
    button = spawn("button", 64, 192);
    door = getGameWorld().create("closedDoor", new SpawnData(32, 96).put("door", "E"));
    //button.<Entity>get("door");
    //button = spawnData.get("door");
  }

}
