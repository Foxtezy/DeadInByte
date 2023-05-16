package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.set;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.lengthOfCell;

import com.almasb.fxgl.core.collection.grid.Grid;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.environment.levelLoader.LevelSetter;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;
import ru.nsu.fit.dib.projectdib.environment.tmxbuilder.LevelToTmx;
import ru.nsu.fit.dib.projectdib.newMultiplayer.config.ClientConfig;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.Sender;
import ru.nsu.fit.dib.projectdib.newMultiplayer.threads.ClientReceiverThread;

public class MapInitializer {

  public static volatile Integer mapSeed = null;


  public Level run() {
    Sender sender = new Sender();
    sender.send(MCClient.getClientSocket(), new Pair<>(MessageType.MAP_SEED, 0));
    ClientConfig.addClientReceiverThread(new ClientReceiverThread());
    while (mapSeed == null);
    Level lvl = new Level(mapSeed, 64, 64, 1, 15);
    lvl.print();
    String levelName = "levels/" + LevelToTmx.levelToTmx(lvl);
    LevelSetter.setLevelFromMap(levelName, getGameWorld());
/*    AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), FXGLForKtKt.getAppWidth(), getAppHeight(), lengthOfCell, lengthOfCell,
            (entityType) -> {
              if (entityType == EntityType.WALL) {
                return CellState.NOT_WALKABLE;
              }
              return CellState.WALKABLE;
            });
    set("grid", grid);*/
//    System.out.println("CELLS: " + grid.getCells().stream().filter(cell -> cell.isWalkable()).toList());

    return lvl;
  }

}
