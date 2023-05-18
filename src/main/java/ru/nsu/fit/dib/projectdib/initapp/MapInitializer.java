package ru.nsu.fit.dib.projectdib.initapp;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

import javafx.util.Pair;
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
    String levelName = "levels/" + LevelToTmx.levelToTmx(lvl);
    LevelSetter.setLevelFromMap(levelName, getGameWorld());
    return lvl;
  }

}
