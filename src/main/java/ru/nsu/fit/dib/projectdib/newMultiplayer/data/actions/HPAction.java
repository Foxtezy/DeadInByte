package ru.nsu.fit.dib.projectdib.newMultiplayer.data.actions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import javafx.application.Platform;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.data.Sounds;
import ru.nsu.fit.dib.projectdib.entity.components.view.HPViewComponent;
import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class HPAction extends GameAction {
  private final int attackingID;
  private final int attackedID;
  private final int attackedHP;

  public HPAction(int attackingID, int attackedID, int attackedHP){

    this.attackingID = attackingID;
    this.attackedID = attackedID;
    this.attackedHP = attackedHP;
  }
  @Override
  public void run() {
    var table = MCClient.getClientState().getIdHashTable();
    Entity attacked = table.get(attackedID);
    var HPComponent = attacked.getComponent(HealthIntComponent.class);
    if (MCClient.getClientId() != 1) HPComponent.setValue(attackedHP);
    if (MCClient.getClientState().getIdHashTable().get(attackedID).getType() == EntityType.PLAYER) {
      attacked.getComponent(HPViewComponent.class).updateHPBar();
    }
    System.out.println(attacked.getType()+" HP:"+HPComponent.getValue());
    if (HPComponent.isZero()){
      Platform.runLater(()->{
        if (attacked.getType() == EntityType.PLAYER) {
          FXGL.play(Sounds.death);
          // TODO: 09.05.2023 game over
          return;
        }
        table.remove(attackedID);
        attacked.removeFromWorld();
      });
    }
  }
  public int getAttackingID() {
    return attackingID;
  }

  public int getAttackedID() {
    return attackedID;
  }

  public int getAttackedHP() {
    return attackedHP;
  }
}
