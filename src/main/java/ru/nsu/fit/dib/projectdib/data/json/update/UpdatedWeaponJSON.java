package ru.nsu.fit.dib.projectdib.data.json.update;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

/**
 * JSON для обновления оружия userID       - индефикатор владельца оружия (null, если ничье)
 * position     - расположение оружия в мире (null, если есть владелец) rotation     - поворот
 * оружия(null, если есть владелец)
 */
public class UpdatedWeaponJSON {

  private final Integer userID;
  private final Point2D position;
  private final double rotation;

  public UpdatedWeaponJSON(Integer userID, Point2D position, double rotation) {
    this.userID = userID;
    this.position = position;
    this.rotation = rotation;
  }

  public void updateWeapon(Entity weapon) {
    if (userID == null) {
      weapon.setPosition(position);
      weapon.setRotation(rotation);
    }
  }
}
