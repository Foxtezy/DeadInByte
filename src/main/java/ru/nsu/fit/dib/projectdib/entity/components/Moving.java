package ru.nsu.fit.dib.projectdib.entity.components;

public interface Moving {


  /**
   * On moving left
   */
  void left();

  /**
   * On moving right
   */
  void right();

  /**
   * On moving up
   */
  void up();

  /**
   * On moving down
   */
  void down();

  /**
   * On stop
   */
  void stop();
}
