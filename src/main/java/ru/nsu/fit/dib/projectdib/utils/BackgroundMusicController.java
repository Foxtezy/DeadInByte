package ru.nsu.fit.dib.projectdib.utils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAudioPlayer;

import com.almasb.fxgl.audio.Music;
import java.util.List;
import java.util.Objects;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.data.Config;
import ru.nsu.fit.dib.projectdib.data.Musics;

public class BackgroundMusicController {

  private static BackgroundMusicController backgroundMusicController;
  private Music nextMusic;
  private int current=0;
  private List<String> playlist;
  private int time=0;

  private BackgroundMusicController() {
  }

  public double fadeVolume = 1;
  Music currentMusic;

  public static BackgroundMusicController getBackgroundMusicControlleroller() {
    if (backgroundMusicController == null) {
      backgroundMusicController = new BackgroundMusicController();
    }
    return backgroundMusicController;
  }

  public void setMusic(String path) {
    if (currentMusic != null) {
      getAudioPlayer().stopMusic(currentMusic);
    }
    currentMusic = getAssetLoader().loadMusic(path);
    getAudioPlayer().loopMusic(currentMusic); //plays the sound
    currentMusic.getAudio$fxgl_core().setVolume(Config.MUSIC);
  }

  public void setPlaylist(List<String> list) {
    if (currentMusic != null) {
      getAudioPlayer().stopMusic(currentMusic);
    }
    playlist = list;
    current = 0;
    currentMusic = getAssetLoader().loadMusic(list.get(current++));
    getAudioPlayer().loopMusic(currentMusic); //plays the sound
    currentMusic.getAudio$fxgl_core().setVolume(Config.MUSIC);
  }

  public void setVolume(double volume) {
    Config.MUSIC = volume;
    Config.addProperty("MUSIC", volume);
    Config.saveSettings();
    if (fadeVolume > volume) {
      fadeVolume = volume;
    }
    currentMusic.getAudio$fxgl_core().setVolume(Config.MUSIC);
  }

  public double getVolume() {
    return Config.MUSIC;
  }

  public void onUpdate() {
    time++;
    if (nextMusic == null) {
      if (time<4000) return;
      if (current == playlist.size()) {
        current = 0;
      }
      if (RandomSystem.getRandInt(100) > 95) {
        System.out.println("music will be changed");
        nextMusic = getAssetLoader().loadMusic(playlist.get(current++));
      }
    }
    if (fadeVolume <= 0) {
      getAudioPlayer().stopMusic(currentMusic);
      fadeVolume = Config.MUSIC;
      currentMusic = nextMusic;
      getAudioPlayer().loopMusic(currentMusic);
      currentMusic.getAudio$fxgl_core().setVolume(fadeVolume);
      nextMusic = null;
      time=0;
    } else if (nextMusic != null) {
      fadeVolume -= 0.0005;
      System.out.println(fadeVolume);
      currentMusic.getAudio$fxgl_core().setVolume(fadeVolume);
    }
  }
}
