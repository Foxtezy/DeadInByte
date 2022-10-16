package ru.nsu.fit.dib.projectdib.moving;

public interface MovingInterface {


  /**
   * On moving left
   */
  void left();

  /**
   * On moving right
   */
  void right();

  /**
   * On moving forward
   */
  void forward();

  /**
   * On moving backward
   */
  void backward();

  /**
   * On stop
   */
  void stop();
}
