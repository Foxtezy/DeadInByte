package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

import static com.google.javascript.jscomp.jarjar.com.google.common.primitives.UnsignedBytes.toInt;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;

public enum ActionStatus {
  CREATED(0),   //Для клиента
  APPROVED(1),  // принят сервером или им создан
  REFUSED(2),   // отказано
  COMPLETED(3)  //
  ;
  private static final Map<Integer, ActionStatus> map = Arrays.stream(values())
      .collect(
          Collectors.toMap(status ->status.id, status -> status));
  private Integer id;
  ActionStatus(Integer id) {
    this.id=id;
  }

  public static ActionStatus getByID(byte status) {
    Integer i = toInt(status);
    return map.get(i);
  }

  public byte getID() {
    Integer a = 1;
    return a.byteValue();
  }
}