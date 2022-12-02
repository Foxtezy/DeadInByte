package ru.nsu.fit.dib.projectdib.level_generation.Structures;

import static java.lang.Math.abs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.Area;
import ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Edge;
import ru.nsu.fit.dib.projectdib.level_generation.Structures.GraphAndTreeStructures.Area.SizeType;
import ru.nsu.fit.dib.projectdib.level_generation.Level;

public class BPGraph {

  public List<Area> nodesList;
  public List<Edge> traversedEdges;
  public Stack<Edge> edgeList;
  private int numberPoints = 0;

  public BPGraph(BPLeaf tree, Level level) {
    nodesList = new ArrayList<>();
    edgeList = new Stack<>();
    addLeafs(tree);
    nodesList.sort(Comparator.comparing(Area::compare));
    traversedEdges = new ArrayList<>();
  }

  private void addLeafs(BPLeaf tree) {
    BPLeaf left = tree.getLeftChild();
    BPLeaf right = tree.getRightChild();
    if (left == null && right == null) {
      nodesList.add(tree.getArea());
      numberPoints++;
    }
    if (left == null || right == null) {
      return;
    }
    addLeafs(left);
    addLeafs(right);
  }

  public void addEdges(SizeType type, Random rn) {
    for (int a = 0; a < nodesList.size() && nodesList.get(a).getSizeType().size >= type.size; a++) {
      for (int b = 0; b < nodesList.size() && nodesList.get(b).getSizeType().size >= type.size;
          b++) {
        if (a != b) {
          Area fst = nodesList.get(a);
          Area snd = nodesList.get(b);
          if (haveOnePoint(fst, snd) || rn.nextInt(10) > 8) {
            edgeList.push(new Edge(fst, snd));
          }
        }
      }
    }
    edgeList.sort(Comparator.comparing(Edge::compare));
  }

  private Boolean haveOnePoint(Area fst, Area snd) {
    int x1 = fst.getFirstPoint().x + 1;
    int x2 = snd.getFirstPoint().x + 1;
    int y1 = fst.getFirstPoint().y + 1;
    int y2 = snd.getFirstPoint().y + 1;
    int diffX, diffY;
    diffX = x2 - x1;
    diffY = y2 - y1;
    Point a;
    if (diffX > 0)//x1+ x2-
    {
      a = approximateX(fst.getHeight() - 3, x1, diffX);
      x1 = a.x;
      diffX = a.y;
    }
    if (diffX < 0)//x1- x2+
    {
      a = approximateX(snd.getWidth() - 3, x2, diffX);
      x2 = a.x;
      diffX = a.y;
    }
    if (diffY > 0)//y1+ y2-
    {
      a = approximateX(fst.getHeight() - 3, y1, diffY);
      y1 = a.x;
      diffX = a.y;
    }
    if (diffY < 0)//y1- y2+
    {
      a = approximateX(fst.getHeight() - 3, y2, diffY);
      y2 = a.x;
      diffX = a.y;
    }
    if ((x1 == x2 && abs(y1 - y2) == 2)
        || (y1 == y2 && abs(x1 - x2) == 2)) {
      return true;
    }
    return false;
  }

  private Point approximateX(int length, int a, int diff) {
    if (length <= diff) {
      a += length;
      diff -= length;
    } else {
      a += diff;
      diff = 0;
    }
    return new Point(a, diff);
  }
}
