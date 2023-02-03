package ru.nsu.fit.dib.projectdib.level_generation.Structures;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.BIG;
import static ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.MIDDLE;
import static ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.OVERBIG;
import static ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.OVERSMALL;
import static ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.SMALL;

import java.awt.Point;
import java.io.Serializable;
import ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.Area;
import ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType;

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
      area.setSizeType(OVERBIG);
      finalBlock = false;
      return;
    }
    if (side >= 12 && P >= P_BIG) {
      area.setSizeType(BIG);
      return;

    }
    if (side >= 6 && P >= P_MIDDLE) {
      area.setSizeType(MIDDLE);
      if (P / P_MIDDLE < 2) {
        finalBlock = true;
      }
      return;
    }
    if (side >= 4 && P >= P_SMALL) {
      area.setSizeType(SMALL);
      finalBlock = true;
      return;
    }
    area.setSizeType(OVERSMALL);
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
