package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

public class Utils {
  public static void delay(){
    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
