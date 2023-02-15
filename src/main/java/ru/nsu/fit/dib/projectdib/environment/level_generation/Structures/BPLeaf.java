package ru.nsu.fit.dib.projectdib.environment.level_generation.Structures;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.awt.Point;
import java.io.Serializable;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.SizeType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.Area;

public class BPLeaf implements Serializable {

  public static final int P_BIG = 300;
  public static final int P_MIDDLE = 100;
  public static final int P_SMALL = 50;
  public static final int P_OVERBIG = 700;
  private final Area area;
  public int P;
  public boolean finalBlock = false;
  private BPLeaf leftChild;
  private BPLeaf rightChild;

  public BPLeaf(Point firstPoint, Point secondPoint) {
    area = new Area(firstPoint, secondPoint);
  }

  public BPLeaf getLeftChild() {
    return leftChild;
  }

  public void setLeftChild(BPLeaf leftChild) {
    this.leftChild = leftChild;
  }

  public BPLeaf getRightChild() {
    return rightChild;
  }

  public void setRightChild(
      BPLeaf rightChild) {
    this.rightChild = rightChild;
  }

  public void setAreaSize() {
    Point first = area.getFirstPoint();
    Point second = area.getSecondPoint();
    int blockWidth = abs(first.x - second.x) + 1;
    int blockHeight = abs(first.y - second.y) + 1;
    int side = min(blockHeight, blockWidth);
    P = blockHeight * blockWidth;
    if (P >= P_OVERBIG) {
      area.setSizeType(SizeType.OVERBIG);
      finalBlock = false;
      return;
    }
    if (side >= 12 && P >= P_BIG) {
      area.setSizeType(SizeType.BIG);
      return;

    }
    if (side >= 6 && P >= P_MIDDLE) {
      area.setSizeType(SizeType.MIDDLE);
      if (P / P_MIDDLE < 2) {
        finalBlock = true;
      }
      return;
    }
    if (side >= 4 && P >= P_SMALL) {
      area.setSizeType(SizeType.SMALL);
      finalBlock = true;
      return;
    }
    area.setSizeType(SizeType.OVERSMALL);
    finalBlock = false;
  }

  public Area getArea() {
    return area;
  }

  public Point getFirstPoint() {
    return area.getFirstPoint();
  }

  public Point getSecondPoint() {
    return area.getSecondPoint();
  }

  public SizeType getSizeType() {
    return area.getSizeType();
  }
}
