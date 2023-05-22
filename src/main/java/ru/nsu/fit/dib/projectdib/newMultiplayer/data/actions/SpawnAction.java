package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import com.almasb.fxgl.dsl.FXGL;
import java.util.Objects;
import javafx.application.Platform;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.entity.creatures.TypeChooser;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;
import ru.nsu.fit.dib.projectdib.utils.SoundsController;

public class SpawnAction extends GameAction {

  public NewEntity getNewEntity() {
    return newEntity;
  }

  private final NewEntity newEntity;

  public SpawnAction(NewEntity newEntity){
    this.newEntity=newEntity;
  }
  @Override
  public void run() {
    //newEntity.spawn();
    switch (TypeChooser.getTypeByString(newEntity.getEntityType())){
      case PROJECTILE -> {
        Projectiles projectile = Projectiles.getByName(newEntity.getEntityType());
        if (projectile!=null)
        {
          switch (projectile) {
            case ARROW -> SoundsController.getSoundsController().play(Sounds.bow_shot);
            case BULLET -> SoundsController.getSoundsController().play(Sounds.bullet_shot);
            case FIREBALL -> SoundsController.getSoundsController().play(Sounds.fireball);
          }
        }
      }
      case PLAYER -> {
        if (!Objects.equals(newEntity.getID(), MCClient.getClientId())) {
          SoundsController.getSoundsController().play(Sounds.connect);
        }
      }
      default -> {}
    }
    Platform.runLater(newEntity::spawn);
  }
}
