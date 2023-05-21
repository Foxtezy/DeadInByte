package ru.nsu.fit.dib.projectdib.utils;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAudioPlayer;

import com.almasb.fxgl.audio.Audio;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;

public class BackgroundMusicController {
  private static BackgroundMusicController backgroundMusicController;
  private BackgroundMusicController(){}

  Music currentMusic;
  public static BackgroundMusicController getBackgroundMusicControlleroller(){
    if (backgroundMusicController==null){
      backgroundMusicController = new BackgroundMusicController();
    }
    return backgroundMusicController;
  }

  public void setMusic(String path){
    if (currentMusic!=null){
      getAudioPlayer().stopMusic(currentMusic);

    }
    currentMusic = getAssetLoader().loadMusic(path);
    getAudioPlayer().loopMusic(currentMusic); //plays the sound
  }

  public void updateSound(){

  };
}
