import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSDeserializer;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.serialization.FBSSerializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.ActionStatus;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.NewEntity;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions.SpawnAction;

public class FlatBurrersTest {

  @Test
  public void UpdateDataTest(){
    List<EntityState> list = new ArrayList<>();
    list.add(new EntityState(1,new Point2D(1,4),new Point2D(3,1),2));
    list.add(new EntityState(3,new Point2D(5,6),new Point2D(6,3),4));

    ByteBuffer buffer = FBSSerializer.serialize(list);

    List<EntityState> newList = FBSDeserializer.deserializeEntityStateList(buffer);
    for(int i=0;i<newList.size();i++){
      Assertions.assertEquals(list.get(i).getId(),newList.get(i).getId());
      Assertions.assertEquals(list.get(i).getPosition().getX(),newList.get(i).getPosition().getX());
      Assertions.assertEquals(list.get(i).getPosition().getY(),newList.get(i).getPosition().getY());
      Assertions.assertEquals(list.get(i).getRotation().getX(),newList.get(i).getRotation().getX());
      Assertions.assertEquals(list.get(i).getRotation().getY(),newList.get(i).getRotation().getY());
      Assertions.assertEquals(list.get(i).getActiveWeapon(),newList.get(i).getActiveWeapon());
    }
  }
  @Test
  public  void SpawnDataTest(){
    EntityState state = new EntityState(123,new Point2D(12,23),new Point2D(34,45),1234);
    NewEntity newEntity = new NewEntity(HeroType.Elf.name(), 234,state);
    SpawnAction sa = new SpawnAction(ActionStatus.APPROVED,123,newEntity);
    ByteBuffer buffer = FBSSerializer.serialize(sa);

    SpawnAction newSA = FBSDeserializer.deserializeSpawnAction(buffer);

    Assertions.assertEquals(sa.getId(),newSA.getId());
    Assertions.assertEquals(sa.getStatus(), newSA.getStatus());
    Assertions.assertEquals(sa.getNewEntity().getSeed(),newSA.getNewEntity().getSeed());
    Assertions.assertEquals(sa.getNewEntity().getID(),newSA.getNewEntity().getID());
    Assertions.assertEquals(sa.getNewEntity().getWeaponId(),newSA.getNewEntity().getWeaponId());
    Assertions.assertEquals(sa.getNewEntity().getEntityType(),newSA.getNewEntity().getEntityType());


    Assertions.assertEquals(sa.getNewEntity().getState().getPosition(),newSA.getNewEntity().getState().getPosition());
    Assertions.assertEquals(sa.getNewEntity().getState().getRotation(),newSA.getNewEntity().getState().getRotation());
    Assertions.assertEquals(sa.getNewEntity().getState().getId(),newSA.getNewEntity().getState().getId());
    Assertions.assertEquals(sa.getNewEntity().getState().getActiveWeapon(),newSA.getNewEntity().getState().getActiveWeapon());
  }

}