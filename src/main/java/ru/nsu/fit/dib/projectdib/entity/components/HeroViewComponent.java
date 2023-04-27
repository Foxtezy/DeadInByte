package ru.nsu.fit.dib.projectdib.entity.components;

import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_height;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_numberColumns;
import static ru.nsu.fit.dib.projectdib.data.ProjectConfig._player_width;

import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class HeroViewComponent extends CreatureViewComponent {

  private static final Image image = new Image(_player);

  public HeroViewComponent(int heroNumber) {
    super(new AnimationChannel(image,
        _player_numberColumns, _player_width, _player_height, Duration.millis(300),
        heroNumber * 13, 1 + heroNumber * 13));
    addAnimationChannel(State.MOVING, new AnimationChannel(image,
        _player_numberColumns, _player_width, _player_height, Duration.millis(300),
        5+heroNumber * 13, 8 + heroNumber * 13));
    addAnimationChannel(State.WAITING,
        new AnimationChannel(image,
            _player_numberColumns, _player_width, _player_height, Duration.millis(450),
            2 + heroNumber * 13, 4 + heroNumber * 13));
    addAnimationChannel(State.BACK,new AnimationChannel(image,
        _player_numberColumns, _player_width, _player_height, Duration.millis(600),
        9 + heroNumber * 13, 12 + heroNumber * 13));
  }

}
