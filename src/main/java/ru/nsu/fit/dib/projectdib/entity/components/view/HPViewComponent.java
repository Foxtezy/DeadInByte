package ru.nsu.fit.dib.projectdib.entity.components.view;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig.heroes_image;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.component.Component;
import javafx.application.Platform;
import javafx.scene.Node;
import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.entity.creatures.CreatureType;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.creatures.TypeChooser;
import ru.nsu.fit.dib.projectdib.ui.HPBar;

public class HPViewComponent extends Component {
  private EntityType entityType;
  private HPBar hpBar;

  public HPViewComponent(CreatureType type, int maxValue) {
    System.out.println("player "+type+", max:"+maxValue);
    if (TypeChooser.getTypeByString(type.getName())==EntityType.PLAYER){
      hpBar = new HPBar(maxValue);
      hpBar.setIcon(heroes_image);
      hpBar.setViewport(((HeroType) type).getIcon());
      hpBar.setScale(2.0);
      hpBar.setProgress(1);
      entityType=EntityType.PLAYER;
    }
    else if (TypeChooser.getTypeByString(type.toString())==EntityType.ENEMY){
      // TODO: 14.05.2023
    }
    else throw new IllegalArgumentException("wrong type");
  }

  public void updateHPBar() {
    if (entityType==EntityType.PLAYER) {
      Platform.runLater(()->{
        hpBar.setValue(getEntity().getComponent(HealthIntComponent.class).getValue());
      });
    }
  }

  public Node getHPBar() {
    return hpBar;
  }
}
