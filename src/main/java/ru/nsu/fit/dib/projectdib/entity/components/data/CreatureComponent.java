package ru.nsu.fit.dib.projectdib.entity.components.data;

import com.almasb.fxgl.entity.component.Component;
import ru.nsu.fit.dib.projectdib.entity.components.view.CreatureViewComponent;
import ru.nsu.fit.dib.projectdib.entity.components.view.HeroViewComponent;
import ru.nsu.fit.dib.projectdib.entity.creatures.Creature;

/**
 * Only Data
 */
public class CreatureComponent extends Component {

  protected Creature creature;

  public CreatureComponent(Creature creature) {
    this.creature=creature;
  }


  public Creature getCreature() {
    return creature;
  }

  public int getAttack() {
    return 0;
  }

  public int getDamage() {
    return 0;
  }

}
