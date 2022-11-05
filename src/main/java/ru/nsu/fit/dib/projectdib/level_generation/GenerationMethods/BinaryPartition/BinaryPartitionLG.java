package ru.nsu.fit.dib.projectdib.level_generation.GenerationMethods.BinaryPartition;

import static java.lang.Math.abs;
import static ru.nsu.fit.dib.projectdib.level_generation.GenerationMethods.BinaryPartition.Structures.BPLeaf.P_BIG;
import static ru.nsu.fit.dib.projectdib.level_generation.GenerationMethods.BinaryPartition.Structures.BPLeaf.P_MIDDLE;

import java.awt.Point;
import java.util.Random;
import ru.nsu.fit.dib.projectdib.level_generation.Area.AreaSizeType.SizeType;
import ru.nsu.fit.dib.projectdib.level_generation.GenerationMethods.BinaryPartition.Structures.BPLeaf;
import ru.nsu.fit.dib.projectdib.level_generation.Level;

/**
 * Генерация с помощью бинарного разделения пространства на области
 */
public class BinaryPartitionLG {

  //RANDOM BOUNDS
  public static final int CUTTING_AXIS = 5;
  public static final int MIN_CUTTING_BLOCK_SIDE = 3;
  //RANDOM BOUNDS
  private final Level level;
  public final int requiredNumberOfBigAreas;
  public final int requiredNumberOfMiddleAreas;
  public int numberOfBigAreas;
  public int numberOfMiddleAreas;
  private final Random rn;

  /**
   * инциализация карты
   *
   * @param level               - уровень(карта)
   * @param numberOfBigAreas    - количество больших областей
   * @param numberOfMiddleAreas - количество мальеньких областей
   */

  public BinaryPartitionLG(Level level, int numberOfBigAreas, int numberOfMiddleAreas) {
    this.level = level;
    this.rn = new Random(level.getSeed());
    this.requiredNumberOfBigAreas = numberOfBigAreas;
    this.requiredNumberOfMiddleAreas = numberOfMiddleAreas;
    this.numberOfBigAreas = 0;
    this.numberOfMiddleAreas = 0;
  }

  /**
   * Разделить блок на 2 (потомка)
   *
   * @param tree - текущая вершина
   */
  public void setPartition(BPLeaf tree) {

    Point first = tree.getFirstPoint();
    Point second = tree.getSecondPoint();
    int blockWidth = abs(first.x - second.x);
    int blockHeight = abs(first.y - second.y);
    if (blockWidth - blockHeight > (rn.nextInt(CUTTING_AXIS))
        && blockWidth > MIN_CUTTING_BLOCK_SIDE * 2) {
      //cut vertically
      int newX = MIN_CUTTING_BLOCK_SIDE + rn.nextInt(blockWidth - MIN_CUTTING_BLOCK_SIDE * 2);
      BPLeaf left = new BPLeaf(first, new Point(first.x + newX, second.y));
      BPLeaf right = new BPLeaf(new Point(first.x + newX, first.y), second);
      setChildren(tree, left, right);
    } else if (blockHeight > MIN_CUTTING_BLOCK_SIDE) {
      //cut horizontally
      int newY = MIN_CUTTING_BLOCK_SIDE + rn.nextInt(blockHeight - MIN_CUTTING_BLOCK_SIDE * 2);
      BPLeaf left = new BPLeaf(first, new Point(second.x, first.y + newY));
      BPLeaf right = new BPLeaf(new Point(first.x, first.y + newY), second);
      setChildren(tree, left, right);
    } else {
      //final block
      tree.setAreaSize();
      SizeType rs = tree.getSizeType();
      switch (rs) {
        case BIG -> {
          numberOfBigAreas++;
          tree.finalBlock = true;
        }
        case MIDDLE -> {
          numberOfMiddleAreas++;
          tree.finalBlock = true;
        }
      }
    }
  }

  /**
   * Добавить блоку потомков. Если один из потомков OVERSMALL то блок становится конечным.
   *
   * @param tree  - текущая вершина
   * @param left  - левый(верхний) потомок
   * @param right - правый(нижний) потомок
   */
  private void setChildren(BPLeaf tree, BPLeaf left, BPLeaf right) {
    finalizeBlock(left);
    finalizeBlock(right);
    if (left.getSizeType() == SizeType.OVERSMALL || right.getSizeType() == SizeType.OVERSMALL) {
      tree.finalBlock = true;
      return;
    }
    tree.setLeftChild(left);
    if (!left.finalBlock) {
      setPartition(left);
    }
    tree.setRightChild(right);
    if (!right.finalBlock) {
      setPartition(right);
    }
  }

  /**
   * Определяет финальный (конечный) блок или нет OVERSMALL, OVERBIG по умолчанию не конечные SMALL
   * по умолчанию конечный
   *
   * @param leaf - проверяющийся блок Результат записывается в finalBlock
   */
  private void finalizeBlock(BPLeaf leaf) {
    leaf.setAreaSize();
    SizeType rs = leaf.getSizeType();
    switch (rs) {
      case BIG -> {
        int numAreas = requiredNumberOfBigAreas - numberOfBigAreas;
        if (numAreas > 0 && (rn.nextInt(numAreas) > 0 || leaf.P / P_BIG < 2)) {
          numberOfBigAreas++;
          leaf.finalBlock = true;
        } else {
          leaf.finalBlock = false;
        }
      }
      case MIDDLE -> {
        int numAreas = requiredNumberOfMiddleAreas - numberOfMiddleAreas;
        if (numAreas > 0 && (rn.nextInt(numAreas) > 0 || leaf.P / P_MIDDLE < 2)) {
          numberOfMiddleAreas++;
          leaf.finalBlock = true;
        } else {
          leaf.finalBlock = false;
        }
      }
    }
  }

  /**
   * Отобразить разделение на карте
   *
   * @param tree - вершина
   */
  public void printPartition(BPLeaf tree) {
    Point first = tree.getFirstPoint();
    Point second = tree.getSecondPoint();
    Point supportive1 = new Point(first.x, second.y);
    Point supportive2 = new Point(second.x, first.y);
    if (tree.getLeftChild() == null && tree.getRightChild() == null) {
      level.tileType = 1;
      level.set(first, second);
      level.tileType = 20;
      level.set(first, supportive1);
      level.set(supportive1, second);
      level.set(supportive2, second);
      level.set(first, supportive2);
    }
    if (tree.getLeftChild() != null) {
      printPartition(tree.getLeftChild());
    }
    if (tree.getRightChild() != null) {
      printPartition(tree.getRightChild());
    }
  }

}
