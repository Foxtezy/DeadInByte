package ru.nsu.fit.dib.projectdib.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TreeNode<T> {
  private TreeNode<T> parent;
  private T root;
  private List<TreeNode<T>> leafs;
  private TreeNode<T> activeNode;
  public TreeNode(T root) {
    parent=this;
    this.root = root;
  }
  private TreeNode(TreeNode<T> parent, T root) {
    this.parent=parent;
    this.root = root;
  }
  public TreeNode(T root, List<T> leafs){
    parent=this;
    activeNode=this;
    addNodes(leafs);
    this.root=root;
  }
  public T getRoot(){
    return root;
  }
  public TreeNode<T> getNode(T branchRoot){
    if (root==branchRoot) return this;
    if (leafs==null) return null;
    for (TreeNode<T> leaf : leafs){
      TreeNode<T> ans = leaf.getNode(branchRoot);
      if  (ans!=null)
      {
        return ans;
      }
    }
    return null;
  }
  public void addNodes(List<T> branchLeafs){
    leafs=new ArrayList<>();
    branchLeafs.forEach(leaf->{
      this.leafs.add(new TreeNode<>(leaf));
    });
  }
  public void addNodes(T branchRoot, List<T> branchLeafs){
    TreeNode<T> localRoot = getNode(branchRoot);
    localRoot.addNodes(branchLeafs);
  }
  public void changeActiveNode(T node){
    activeNode=getNode(node);
  }
  public List<T> getChildren(){
    List<T> list = new ArrayList<>();
    leafs.forEach(nowNode->{list.add(nowNode.getRoot());});
    return list;
  }
  public List<T> getParentARChildren(){
    return activeNode.parent.getChildren();
  }
  public List<T> getARChildren(){
    return activeNode.getChildren();
  }
  public T getParent(){
    return parent.getRoot();
  }
}
