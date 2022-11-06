package ru.nsu.fit.dib.projectdib.mapperobjects;

import java.util.Objects;
import ru.nsu.fit.dib.projectdib.EntityType;

public record GameObject(Integer id, EntityType type, Integer x, Integer y, Integer wight,
                         Integer height) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GameObject gameObject)) {
      return false;
    }
    return id.equals(gameObject.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
