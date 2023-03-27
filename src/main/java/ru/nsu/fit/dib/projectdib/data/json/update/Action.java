package ru.nsu.fit.dib.projectdib.data.json.update;

/**
 * Действие существа. action - тип действия objectOfAction - объект над которым
 * совершается действие(null, если действие осуществляется не над каким либо объектом)
 */
public class Action {

  ActionType type;
  Integer objectOfAction;

  public Action(ActionType type, Integer objectOfAction) {
    this.type = type;
    this.objectOfAction = objectOfAction;
  }

  public ActionType getType() {
    return type;
  }

  public Integer getObjectOfAction() {
    return objectOfAction;
  }
}
