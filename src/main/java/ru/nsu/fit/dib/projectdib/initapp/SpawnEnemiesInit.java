package ru.nsu.fit.dib.projectdib.initapp;

import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.RoomGeneration.RoomType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.SizeType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;

public class SpawnEnemiesInit {

  private final Random rand = new Random();

  public void run(BlockingQueue<Pair<MessageType, Object>> actionQueue) {
    List<Pair<SizeType, Rectangle2D>> rooms = GameInitializer.lvl.roomList.stream()
        .map(r -> new Pair<>(r.getSizeType(), new Rectangle2D(r.getFirstPoint().x * 160, r.getFirstPoint().y * 160,
            (r.getSecondPoint().x - r.getFirstPoint().x) * 160,
            (r.getSecondPoint().y - r.getFirstPoint().y) * 160))).filter(p -> !p.getValue().contains(GameInitializer.start)).toList();
    int j = 0;
    for (int i = 0; i < rooms.size(); i++) {
      switch (rooms.get(i).getKey()) {
        case SMALL -> {
          spawnEnemy(actionQueue, rooms, i, j);
          j+=4;
        }
        case MIDDLE -> {
          spawnEnemy(actionQueue, rooms, i, j);
          spawnEnemy(actionQueue, rooms, i, j + 1);
          spawnEnemy(actionQueue, rooms, i, j + 2);
          j+=5;
        }
        case BIG, OVERBIG -> {
          spawnEnemy(actionQueue, rooms, i, j);
          spawnEnemy(actionQueue, rooms, i, j + 1);
          spawnEnemy(actionQueue, rooms, i, j + 2);
          spawnEnemy(actionQueue, rooms, i, j + 3);
          j+=6;
        }
      }
    }
  }

  private void spawnEnemy(BlockingQueue<Pair<MessageType, Object>> actionQueue, List<Pair<SizeType, Rectangle2D>> rooms, Integer i, Integer j) {
    try {
      int x = rand.nextInt((int) rooms.get(i).getValue().getMinX(),
          (int) rooms.get(i).getValue().getMaxX());
      int y = rand.nextInt((int) rooms.get(i).getValue().getMinY(),
          (int) rooms.get(i).getValue().getMaxY());
      EnemyType name = EnemyType.values()[rand.nextInt(EnemyType.values().length)];
      Integer seed = rand.nextInt();
      actionQueue.add(new Pair<>(MessageType.SPAWN,
          new SpawnAction(
              new NewEntity(name.getName(), seed, new EntityState(10230 + i + j,
                  new Point2D(x, y), new Point2D(0, 0), -1)))));
      Creature creature = EnemiesFactory.newEnemy(name, seed);
      actionQueue.add(new Pair<>(MessageType.SPAWN, new SpawnAction(
          new NewEntity(creature.getStandardWeapon().getName(), seed,
              new EntityState(30080 + i + j, new Point2D(x, y), new Point2D(0, 0), -1)))));
    } catch (Exception e) {

    }
  }
}
