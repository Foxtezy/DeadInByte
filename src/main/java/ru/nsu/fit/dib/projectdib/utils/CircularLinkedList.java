package ru.nsu.fit.dib.projectdib.utils;

import java.util.ArrayList;

public class CircularLinkedList<T> extends ArrayList<T> {
  private int capacity;
  private int position;
  public CircularLinkedList(int capacity) {
    super(capacity);
    position=0;
    this.capacity=capacity;
    for (int i=0;i<capacity;i++){
      super.add(null);
    }
  }

  public T getCurrent(){
    return super.get(position);
  }
  public T next(){
    if (position==capacity-1) position=-1;
    return super.get(++position);
  }
  public T previous(){
    if (position==0) position=capacity-1;
    return super.get(position);
  }
  public T removeCurrent(){
    capacity--;
    return super.remove(position);
  }
  public void addCurrent(T o){
    capacity++;
    super.add(position,o);
  }
  public T replaceCurrent(){
    T o = super.remove(position);
    super.add(position,null);
    return o;
  }
  public T replaceCurrent(T o){
    T o2 = super.remove(position);
    super.add(position,o);
    return o2;
  }
  public void addCapacity(int value){
    for (int i=0;i<value;i++){
      super.add(null);
    }
    capacity+=value;
  }
  public void removeCapacity(int value){
    for (int i=0;i<value;i++){
      super.remove(capacity-1-i);
    }
    capacity-=value;
  }
}
