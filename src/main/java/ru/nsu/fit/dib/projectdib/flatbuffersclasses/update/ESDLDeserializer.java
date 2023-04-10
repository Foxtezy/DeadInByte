package ru.nsu.fit.dib.projectdib.flatbuffersclasses.update;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;

public class ESDLDeserializer {

  public static List<EntityState> deserialize(ByteBuffer buffer) {
    EntityStateDataList entityStateDataList = EntityStateDataList.getRootAsEntityStateDataList(
        buffer);
    List<EntityState> list = new ArrayList<>();
    for (int i = 0; i < entityStateDataList.listLength(); i++) {
      list.add(deserializeEntityState(entityStateDataList.list(i)));
    }
    return list;
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
