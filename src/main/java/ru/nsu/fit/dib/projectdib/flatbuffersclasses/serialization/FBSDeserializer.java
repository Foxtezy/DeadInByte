package ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.EntityStateDataList;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.HPActionData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.NewEntityData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.SpawnActionData;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.WeaponActionData;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.HPAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.WeaponAction.WeaponActionType;

public class FBSDeserializer {

  public static List<EntityState> deserializeEntityStateList(ByteBuffer buffer) {
    EntityStateDataList entityStateDataList = EntityStateDataList.getRootAsEntityStateDataList(buffer);
    List<EntityState> list = new ArrayList<>();
    for (int i = 0; i < entityStateDataList.listLength(); i++) {
      list.add(deserializeEntityState(entityStateDataList.list(i)));
    }
    return list;
  }

  public static SpawnAction deserializeSpawnAction(ByteBuffer buffer) {
    SpawnActionData spawnActionData = SpawnActionData.getRootAsSpawnActionData(buffer);
    return new SpawnAction(deserializeNewEntity(spawnActionData.data()));
  }
  public static WeaponAction deserializeWeaponAction(ByteBuffer buffer){
    WeaponActionData weaponActionData = WeaponActionData.getRootAsWeaponActionData(buffer);
    return new WeaponAction(WeaponActionType.getByID(weaponActionData.action()),weaponActionData.user(),weaponActionData.weapon());
  }
  public static HPAction deserializeHPAction(ByteBuffer buffer){
    HPActionData hpActionData = HPActionData.getRootAsHPActionData(buffer);
    return new HPAction(hpActionData.attackingId(),hpActionData.attackedId(),hpActionData.attackedHp());
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
      ru.nsu.fit.dib.projectdib.flatbuffersclasses.generated.Point2D point) {
    return new Point2D(point.x(), point.y());
  }

}
