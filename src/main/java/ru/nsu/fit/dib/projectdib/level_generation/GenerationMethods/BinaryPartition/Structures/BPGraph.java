package ru.nsu.fit.dib.projectdib.level_generation.GenerationMethods.BinaryPartition.Structures;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.fit.dib.projectdib.level_generation.Area.Area;

public class BPGraph {
  public List<Area> list;
  public BPGraph(BPLeaf tree)
  {
    list=new ArrayList<>();
    addLeafs(tree);
  }
  private void addLeafs(BPLeaf tree)
  {
    BPLeaf left = tree.getLeftChild();
    BPLeaf right = tree.getRightChild();
    if (left==null && right==null) list.add(tree.getArea());
    if (left==null || right==null) return;
    addLeafs(left);
    addLeafs(right);
  }
}
