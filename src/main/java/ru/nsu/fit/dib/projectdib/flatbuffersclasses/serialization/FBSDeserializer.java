package ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.EntityStateData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.EntityStateDataList;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.NewEntityData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.SpawnActionData;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionStatus;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;

public class FBSDeserializer {

  public static List<EntityState> deserializeEntityStateList(ByteBuffer buffer) {
    EntityStateDataList entityStateDataList = EntityStateDataList.getRootAsEntityStateDataList(
        buffer);
    List<EntityState> list = new ArrayList<>();
    for (int i = 0; i < entityStateDataList.listLength(); i++) {
      list.add(deserializeEntityState(entityStateDataList.list(i)));
    }
    return list;
  }

  public static SpawnAction deserializeSpawnAction(ByteBuffer buffer) {
    SpawnActionData spawnActionData = SpawnActionData.getRootAsSpawnActionData(buffer);
    SpawnAction spawnAction = new SpawnAction(ActionStatus.getByID(spawnActionData.status()),
        spawnActionData.id(),
        deserializeNewEntity(spawnActionData.data()));
    return spawnAction;
  }

  private static NewEntity deserializeNewEntity(NewEntityData newEntityData) {
    return new NewEntity(newEntityData.type(),
        newEntityData.seed(),
        deserializeEntityState(newEntityData.state()));
  }

  private static EntityState deserializeEntityState(EntityStateData stateData) {
    return new EntityState(stateData.id(),
        deserializePoint2D(stateData.pos()),
        deserializePoint2D(stateData.rot()),
        stateData.activeWeapon());
  }

  private static Point2D deserializePoint2D(
      ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.Point2D point) {
    return new Point2D(point.x(), point.y());
  }

}
