package ru.nsu.fit.dib.projectdib.ui.UIElements;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
  final int columns;
  final int count;
  final int offsetX;
  final int offsetY;
  final int width;
  final int height;
  ImageView imageView;

  public SpriteAnimation(ImageView imageView, Duration duration, int columns, int count,
      int offsetX, int offsetY, int width, int height) {
    this.imageView = imageView;
    this.columns = columns;
    this.count = count;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.width = width;
    this.height = height;
    setCycleDuration(duration);
  }

  @Override
  protected void interpolate(double position) {
    int index = Math.min((int) Math.floor(position * count), count - 1);
    int x = (index % columns) * width + offsetX;
    int y = (index / columns) * width + offsetY;
    imageView.setViewport(new Rectangle2D(x,y,width,height));
  }
}
