package ru.nsu.fit.dib.projectdib.mapperobjects;

import java.util.Objects;
import ru.nsu.fit.dib.projectdib.EntityType;

/**
 * Класс описывающий физический объект
 * (используется при загрузке чанков)
 */
public record PhysicalObject(Integer id, EntityType type, Integer x, Integer y, Integer width,
                             Integer height) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PhysicalObject physicalObject)) {
      return false;
    }
    return id.equals(physicalObject.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
