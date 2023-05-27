package ru.nsu.fit.dib.projectdib.newMultiplayer;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.components.multiplayer.DataComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.GameAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.HPAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction.WeaponActionType;

public class ServerState {


  private int mapSeed = 0;

  public void generateMapSeed() {
    mapSeed = new Random().nextInt();
  }

  public int getMapSeed() {
    return mapSeed;
  }

  public List<SpawnAction> getInitSpawnList() {
    Map<Integer, Entity> state = MCClient.getClientState().getIdHashTable();
    System.out.println(state.size());
    List<Optional<SpawnAction>> optionalEntities = state.keySet().stream().map(key -> MCClient.getClientState()
        .getSpawnAction(key)).toList();
    List<SpawnAction> spawnList = new ArrayList<>();
    for (Optional<SpawnAction> entityState : optionalEntities) {
      entityState.ifPresent(spawnList::add);
    }
    System.out.println(spawnList);
    return spawnList;
  }

  public List<HPAction> getInitHPactionList() {
    Collection<Entity> entities = MCClient.getClientState().getIdHashTable().values();
    List<HPAction> hpActions = new ArrayList<>();
    for (Entity entity : entities) {
      if (entity.hasComponent(HealthIntComponent.class) && entity.hasComponent(DataComponent.class) && entity.getComponent(DataComponent.class).isValid()) {
        if (entity.getComponent(HealthIntComponent.class).getMaxValue() > entity.getComponent(
            HealthIntComponent.class).getValue()) {
          hpActions.add(new HPAction(0, entity.getComponent(DataComponent.class).getId(), entity.getComponent(
              HealthIntComponent.class).getValue()));
        }
      }
    }
    return hpActions;
  }

  public List<WeaponAction> getInitWeaponActionList() {
    Collection<Entity> entities = MCClient.getClientState().getIdHashTable().values();
    List<WeaponAction> weaponActions = new ArrayList<>();
    for (Entity entity : entities) {
      if (entity.hasComponent(DataComponent.class) && entity.getType() == EntityType.WEAPON && entity.getComponent(DataComponent.class).isValid()) {
        if (entity.getComponent(DataComponent.class).getBindedEntity() != -1) {
          weaponActions.add(new WeaponAction(WeaponActionType.TAKE,
              entity.getComponent(DataComponent.class).getBindedEntity(),
              entity.getComponent(DataComponent.class).getId()));
        }
      }
    }
    return weaponActions;
  }
}
