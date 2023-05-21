package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import com.almasb.fxgl.dsl.FXGL;
import java.util.Objects;
import javafx.application.Platform;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.entity.creatures.TypeChooser;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

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
            case ARROW -> FXGL.play(Sounds.bow_shot);
            case BULLET -> FXGL.play(Sounds.bullet_shot);
            case FIREBALL -> FXGL.play(Sounds.fireball);
          }
        }
      }
      case PLAYER -> {
        if (!Objects.equals(newEntity.getID(), MCClient.getClientId())) {
          FXGL.play(Sounds.connect);
        }
      }
      default -> {}
    }
    Platform.runLater(newEntity::spawn);
  }
}
