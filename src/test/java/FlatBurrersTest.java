import com.almasb.fxgl.core.collection.Array;
import java.awt.image.DataBuffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.ESDLDeserializer;
import ru.nsu.fit.dib.projectdib.flatbuffersclasses.update.ESDLSerializer;
import ru.nsu.fit.dib.projectdib.newMultiplayer.data.EntityState;

public class FlatBurrersTest {

  @Test
  public void UpdateDataTest(){
    List<EntityState> list = new ArrayList<>();
    list.add(new EntityState(1,new Point2D(1,4),new Point2D(3,1),2));
    list.add(new EntityState(3,new Point2D(5,6),new Point2D(6,3),4));

    ByteBuffer buffer = ESDLSerializer.serialize(list);
    System.out.println(buffer);
    List<EntityState> newList = ESDLDeserializer.deserialize(buffer);
    for(int i=0;i<newList.size();i++){
      Assertions.assertEquals(list.get(i).getId(),newList.get(i).getId());
      Assertions.assertEquals(list.get(i).getPosition().getX(),newList.get(i).getPosition().getX());
      Assertions.assertEquals(list.get(i).getPosition().getY(),newList.get(i).getPosition().getY());
      Assertions.assertEquals(list.get(i).getRotation().getX(),newList.get(i).getRotation().getX());
      Assertions.assertEquals(list.get(i).getRotation().getY(),newList.get(i).getRotation().getY());
      Assertions.assertEquals(list.get(i).getActiveWeapon(),newList.get(i).getActiveWeapon());
    }
  }

}
