package ru.nsu.fit.dib.projectdib.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Дерево для UI элементов. Можно осуществлять добавление элементов в определенный Node, получение
 * списка дочерних объектов, дочерних объектов родителя от корня. Имеет активную Node, в которой на
 * данный момент находится Пользователь в меню.
 *
 * @param <T> - тип Node
 */
public class TreeNode<T> {

  private TreeNode<T> parent;
  private T root;
  private List<TreeNode<T>> leafs;
  private TreeNode<T> activeNode;

  /**
   * Конструктор
   *
   * @param root - корень
   */
  public TreeNode(T root) {
    parent = this;
    this.root = root;
  }

  private TreeNode(TreeNode<T> parent, T root) {
    this.parent = parent;
    this.root = root;
  }

  /**
   * Конструктор
   *
   * @param root  - корень
   * @param leafs - листья
   */
  public TreeNode(T root, List<T> leafs) {
    parent = this;
    activeNode = this;
    addNodes(leafs);
    this.root = root;
  }

  /**
   * Получить корень
   *
   * @return - корень
   */
  public T getRoot() {
    return root;
  }

  private TreeNode<T> getNode(T value) {
    if (root == value) {
      return this;
    }
    if (leafs == null) {
      return null;
    }
    for (TreeNode<T> leaf : leafs) {
      TreeNode<T> ans = leaf.getNode(value);
      if (ans != null) {
        return ans;
      }
    }
    return null;
  }

  private void addNodes(List<T> branchLeafs) {
    leafs = new ArrayList<>();
    branchLeafs.forEach(leaf -> {
      this.leafs.add(new TreeNode<>(leaf));
    });
  }

  /**
   * Добавить детей
   *
   * @param branchRoot  - родитель
   * @param branchLeafs - дети
   */
  public void addNodes(T branchRoot, List<T> branchLeafs) {
    TreeNode<T> localRoot = getNode(branchRoot);
    localRoot.addNodes(branchLeafs);
  }

  /**
   * Сменить активную вершину
   *
   * @param node - вершина, на которую нужно переключиться
   */
  public void changeActiveNode(T node) {
    activeNode = getNode(node);
  }

  private List<T> getChildren() {
    List<T> list = new ArrayList<>();
    leafs.forEach(nowNode -> {
      list.add(nowNode.getRoot());
    });
    return list;
  }

  /**
   * Получить детей родителя активной Node
   *
   * @return - список детей
   */
  public List<T> getParentANChildren() {
    return activeNode.parent.getChildren();
  }

  /**
   * Получить детей активной Node
   *
   * @return - список детей
   */
  public List<T> getANChildren() {
    return activeNode.getChildren();
  }

  /**
   * Получить родителя активной Node
   *
   * @return - родитель
   */
  public T getParentAN() {
    return activeNode.root;
  }
}
