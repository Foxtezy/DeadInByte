package ru.nsu.fit.dib.projectdib.environment.level_generation.AreaGeneration;

import static java.lang.Math.abs;
import static ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.SizeType.OVERSMALL;
import static ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.BPLeaf.P_BIG;
import static ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.BPLeaf.P_MIDDLE;

import java.awt.Point;
import java.util.Random;
import ru.nsu.fit.dib.projectdib.environment.level_generation.BlockDensity;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.SizeType;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.BPGraph;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.BPLeaf;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Level;

/**
 * Генерация с помощью бинарного разделения пространства на области
 */
public class BinaryPartitionAG {

  //RANDOM BOUNDS
  public static final int CUTTING_AXIS = 3;
  public static final int MIN_CUTTING_BLOCK_SIDE = 4;
  public final int requiredNumberOfBigAreas;
  public final int requiredNumberOfMiddleAreas;
  //RANDOM BOUNDS
  private final Level level;
  private final Random rn;
  public int numberOfBigAreas;
  public int numberOfMiddleAreas;
  public BPGraph graph;
  /**
   * инциализация карты
   *
   * @param level               уровень(карта)
   * @param numberOfBigAreas    количество больших областей
   * @param numberOfMiddleAreas количество мальеньких областей
   */

  public BinaryPartitionAG(Level level, int numberOfBigAreas, int numberOfMiddleAreas) {
    this.level = level;
    this.rn = level.rn;
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
    } else if (blockHeight > MIN_CUTTING_BLOCK_SIDE * 2) {
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
    //printPartition(tree);
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
    if (left.getSizeType() == OVERSMALL || right.getSizeType() == OVERSMALL) {
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
      level.set(BlockDensity.FLOOR, first, second);
      level.set(BlockDensity.WALL, first, supportive1);
      level.set(BlockDensity.WALL, supportive1, second);
      level.set(BlockDensity.WALL, supportive2, second);
      level.set(BlockDensity.WALL, first, supportive2);
    }
    if (tree.getLeftChild() != null) {
      printPartition(tree.getLeftChild());
    }
    if (tree.getRightChild() != null) {
      printPartition(tree.getRightChild());
    }
  }

  public void createRooms() {
    rn.nextInt(10);
  }
}
