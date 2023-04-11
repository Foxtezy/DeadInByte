package ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization;

import static ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.SpawnActionData.createSpawnActionData;

import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.EntityStateData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.EntityStateDataList;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.NewEntityData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.Point2D;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;

public class FBSSerializer {

  private static final int INITAL_SIZE = 1024;

  public static ByteBuffer serialize(List<EntityState> entityStateList) {
    FlatBufferBuilder builder = new FlatBufferBuilder(INITAL_SIZE);
    int[] entityStateDataList = new int[entityStateList.size()];
    for (int i = 0; i < entityStateList.size(); i++) {
      EntityState state = entityStateList.get(i);
      entityStateDataList[i] = createEntityStateData(builder,state);
    }
    int list = EntityStateDataList.createListVector(builder,entityStateDataList);
    builder.finish(list);
    return builder.dataBuffer();
  }
  public static ByteBuffer serialize(SpawnAction action){
    FlatBufferBuilder builder = new FlatBufferBuilder(INITAL_SIZE);
    int spawnActionData = createSpawnActionData(builder,
        action.getStatus().getID(),
        action.getId(),
        createNewEntityData(builder, action.getNewEntity()));
    builder.finish(spawnActionData);
    return builder.dataBuffer();
  }
  private static int createNewEntityData(FlatBufferBuilder builder, NewEntity newEntity){
    int newEntityData = NewEntityData.createNewEntityData(builder,
        builder.createString(newEntity.getEntityType()),
        newEntity.getSeed(),
        createEntityStateData(builder,newEntity.getState()));
    return newEntityData;
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
