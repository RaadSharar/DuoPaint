package com.example.duopaint;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public class Line implements Serializable {
    public double x1, y1, x2, y2;
    public String strokeColor;
    public double strokeWidth;
    public Line(double x1, double y1, double x2, double y2, Paint strokeColor, double strokeWidth) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.strokeColor = toHexColor((Color) strokeColor);
        this.strokeWidth = strokeWidth;
    }
    private String toHexColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
