package ru.nsu.fit.dib.projectdib.initapp;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Pair;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.socket.MessageType;

public class SpawnEnemiesInit {

  public void run(BlockingQueue<Pair<MessageType, Object>> actionQueue) {
    List<Rectangle2D> rooms = GameInitializer.lvl.roomList.stream()
        .map(r -> new Rectangle2D(r.getFirstPoint().x * 160, r.getFirstPoint().y * 160,
            (r.getSecondPoint().x - r.getFirstPoint().x) * 160,
            (r.getSecondPoint().y - r.getFirstPoint().y) * 160)).toList();
    Random rand = new Random();
    EnemyType name = EnemyType.values()[rand.nextInt(EnemyType.values().length)];
    Integer seed = rand.nextInt();
    actionQueue.add(new Pair<>(MessageType.SPAWN,
        new SpawnAction(
            new NewEntity(name.getName(), seed, new EntityState(1230,
                GameInitializer.start, new Point2D(0, 0), -1)))));
    Creature creature = EnemiesFactory.newEnemy(name,seed);
    actionQueue.add(new Pair<>(MessageType.SPAWN, new SpawnAction(new NewEntity(creature.getStandardWeapon().getName(), seed, new EntityState(8080, GameInitializer.start, new Point2D(0, 0), -1)))));
   /* for (int i = 0; i < rooms.size(); i++) {
      try {
        int x = rand.nextInt((int) rooms.get(i).getMinX(), (int) rooms.get(i).getMaxX());
        int y = rand.nextInt((int) rooms.get(i).getMinY(), (int) rooms.get(i).getMaxY());
        actionQueue.add(new Pair<>(MessageType.SPAWN,
            new SpawnAction(
                new NewEntity(EnemyType.values()[rand.nextInt(EnemyType.values().length)].getName(), rand.nextInt(), new EntityState(1230 + i,
                    new Point2D(x, y), new Point2D(0, 0), -1)))));
        x = rand.nextInt((int) rooms.get(i).getMinX(), (int) rooms.get(i).getMaxX());
        y = rand.nextInt((int) rooms.get(i).getMinY(), (int) rooms.get(i).getMaxY());
        actionQueue.add(new Pair<>(MessageType.SPAWN,
            new SpawnAction(
                new NewEntity(EnemyType.values()[rand.nextInt(EnemyType.values().length)].getName(), rand.nextInt(), new EntityState(2230 + i,
                    new Point2D(x, y), new Point2D(0, 0), -1)))));
      } catch (Exception e) {

      }
    }
  }
    */
  }
}
