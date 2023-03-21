package ru.nsu.fit.dib.projectdib.data.json.update;

import static ru.nsu.fit.dib.projectdib.data.json.update.ActionType.ATTACK;

import com.almasb.fxgl.entity.Entity;
import java.util.List;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.entity.components.CreatureComponent;
import ru.nsu.fit.dib.projectdib.multiplayer.ClientTaskManager;

/**
 * JSON для обновления существа
 * position         - расположение в мире
 * directionView    - направление
 * взгляда actions  - действия, совершенные существом
 */
public class UpdatedCreatureJSON {
  List<Action> actions;
  private final Point2D position;
  private final Point2D directionView;

  public UpdatedCreatureJSON(Point2D position, Point2D directionView, List<Action> actions) {
    this.actions = actions;
    this.position = position;
    this.directionView = directionView;
  }

  public void updateCreature(Entity creature){

    Point2D movingVector = position.add(creature.getPosition().multiply(-1));
    if (movingVector.getX()>0) creature.getComponent(CreatureComponent.class).right();
    else if (movingVector.getX()<0) creature.getComponent(CreatureComponent.class).left();
    if (movingVector.getY()>0) creature.getComponent(CreatureComponent.class).down();
    else if (movingVector.getY()<0) creature.getComponent(CreatureComponent.class).up();

    creature.getComponent(CreatureComponent.class).bindDirectionView(entity -> directionView);

    actions.forEach(action -> {
      switch (action.getType()){
        case ATTACK -> creature.getComponent(CreatureComponent.class).attack();
        case TAKE -> creature.getComponent(CreatureComponent.class).takeWeapon(ClientTaskManager.idHashTable.get(action.getObjectOfAction()));
        case THROW -> creature.getComponent(CreatureComponent.class).throwWeapon();
      }
    });
  }
}
