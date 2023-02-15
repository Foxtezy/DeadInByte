package ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.fit.dib.projectdib.environment.level_generation.Structures.GraphAndTreeStructures.Area.Area;

/**
 * Ребро BP графа.
 *
 */
public class Edge implements Serializable {

  private final List<Area> areaList;
  private Integer weight = 0;

  public Edge(Area firstArea, Area secondArea) {
    areaList = new ArrayList<>();
    if (firstArea.getSize() < secondArea.getSize()) {
      areaList.add(firstArea);
      areaList.add(secondArea);
    } else {
      areaList.add(secondArea);
      areaList.add(firstArea);
    }
    weight = firstArea.getRoom().getWeight() + secondArea.getRoom().getWeight();
  }

  public Integer getWeight() {
    return weight;
  }

  public int compare() {
    return -weight;
  }

  @Override
  public boolean equals(Object o) {
    final Edge edge = (Edge) o;
    return areaList.contains(edge.areaList.get(0)) && areaList.contains(edge.areaList.get(1));
  }

  public Area getArea(int index) {
    return areaList.get(index);
  }
}
