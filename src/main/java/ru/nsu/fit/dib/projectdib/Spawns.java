package ru.nsu.fit.dib.projectdib;

import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;


public class Spawns {
public static Entity button;
  public static void spawnInitialObjects() {
    Map<Integer, Entity> doors = new HashMap<>();
    doors.put(1, spawn("closedDoor", 144, 192));
    Map<Integer, Entity > buttons = new HashMap<>();
    buttons.put(1, spawn("button", 144, 256));
  }

}
