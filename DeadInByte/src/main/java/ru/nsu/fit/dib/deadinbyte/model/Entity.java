package ru.nsu.fit.dib.deadinbyte.model;

import javafx.geometry.Point2D;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Entity extends Body {
    private Integer health;
    private List<Object> inventory; // TODO: 01.10.2022 класс объектов инвенторя
    private Double weight;
    /**
     * вектор скорости
     */
    private Point2D speed;
    private Map<String, Method> actions;

}
