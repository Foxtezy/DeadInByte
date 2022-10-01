package ru.nsu.fit.dib.deadinbyte.model;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Body {
    private Point2D coordinates;
    private String image; // TODO: 01.10.2022 картинка
    private Rectangle2D collider;
}
