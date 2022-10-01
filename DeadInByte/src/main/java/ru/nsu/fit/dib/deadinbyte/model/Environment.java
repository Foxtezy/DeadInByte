package ru.nsu.fit.dib.deadinbyte.model;


import lombok.Data;

@Data
public class Environment extends Body {
    /**
     * Коэффициент трения.
     */
    private Double friction;
}
