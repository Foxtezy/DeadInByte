package ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization;

import static ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.SpawnActionData.createSpawnActionData;
import static ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.WeaponActionData.createWeaponActionData;

import com.google.flatbuffers.FlatBufferBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateDataList;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.NewEntityData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.Point2D;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction;

public class FBSSerializer {

  private static final int INITAL_SIZE = 1024;

  public static ByteBuffer serialize(List<EntityState> entityStateList) {
    FlatBufferBuilder builder = new FlatBufferBuilder(INITAL_SIZE);
    int[] list = new int[entityStateList.size()];
    for (int i = 0; i < entityStateList.size(); i++) {
      EntityState state = entityStateList.get(i);
      list[i] = createEntityStateData(builder,state);
    }
    int i = builder.createVectorOfTables(list);
    int entityStateDataList = EntityStateDataList.createEntityStateDataList(builder,i);
    builder.finish(entityStateDataList);
    return builder.dataBuffer();
  }
  public static ByteBuffer serialize(SpawnAction action){
    FlatBufferBuilder builder = new FlatBufferBuilder(INITAL_SIZE);
    int spawnActionData = createSpawnActionData(builder,
        createNewEntityData(builder, action.getNewEntity()));
    builder.finish(spawnActionData);
    return builder.dataBuffer();
  }
  public static ByteBuffer serialize(WeaponAction action){
    FlatBufferBuilder builder = new FlatBufferBuilder(INITAL_SIZE);
    int weaponActionData = createWeaponActionData(builder,
        action.getAction().getID(),action.getUser(),action.getWeapon());
    builder.finish(weaponActionData);
    return builder.dataBuffer();
  }
  private static int createNewEntityData(FlatBufferBuilder builder, NewEntity newEntity){
    return NewEntityData.createNewEntityData(builder,
        builder.createString(newEntity.getEntityType()),
        newEntity.getSeed(),
        createEntityStateData(builder,newEntity.getState()));
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
