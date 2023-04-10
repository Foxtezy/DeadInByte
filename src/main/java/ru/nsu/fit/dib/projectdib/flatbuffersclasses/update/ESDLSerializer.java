package ru.nsu.fit.dib.projectdib.flatbuffersclasses.update;

import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;

public class ESDLSerializer {

  public static ByteBuffer serialize(List<EntityState> entityStateList) {
    FlatBufferBuilder builder = new FlatBufferBuilder(1024);
    int[] entityStateDataList = new int[entityStateList.size()];
    for (int i = 0; i < entityStateList.size(); i++) {
      EntityState state = entityStateList.get(i);
      entityStateDataList[i] = createEntityStateData(builder,state);
    }
    int list = EntityStateDataList.createListVector(builder,entityStateDataList);
    builder.finish(list);
    return builder.dataBuffer();
  }

  private static int createEntityStateData(FlatBufferBuilder builder, EntityState state) {
    EntityStateData.startEntityStateData(builder);
    EntityStateData.addId(builder, state.getId());
    EntityStateData.addPos(builder, Point2D.createPoint2D(builder,state.getPosition().getX(),state.getPosition().getY()));
    EntityStateData.addRot(builder, Point2D.createPoint2D(builder,state.getRotation().getX(),state.getRotation().getY()));
    EntityStateData.addActiveWeapon(builder, state.getActiveWeapon());
    return EntityStateData.endEntityStateData(builder);
  }
}
