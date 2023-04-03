package ru.nsu.fit.dib.projectdib.newMultiplayer.data;

public enum ActionStatus {
  CREATED, //Для клиента
  APPROVED, // принят сервером или им создан
  REFUSED, // отказано
  COMPLETED //ответ клиета
}