package ru.nsu.fit.dib.projectdib.utils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAudioPlayer;

import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import ru.nsu.fit.dib.projectdib.data.Config;

public class SoundsController {
  private static SoundsController soundsController;
  public static SoundsController getSoundsController() {
    if (soundsController==null){
      soundsController = new SoundsController();
    }
    return soundsController;
  }

  public void setVolume(double newVal) {
    Config.SOUNDS=newVal;
    Config.addProperty("SOUNDS", newVal);
    Config.saveSettings();
  }
  public void play(String path){
    Sound sound = FXGL.getAssetLoader().loadSound(path);
    sound.getAudio$fxgl_core().setVolume(Config.SOUNDS);
    if (Config.SOUNDS!=0) {
      getAudioPlayer().playSound(sound);
    }
  }

  public double getVolume() {
    return Config.SOUNDS;
  }
}
