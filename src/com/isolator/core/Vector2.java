package com.isolator.core;

public class Vector2 {

    private double x;
    private double y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    private double getLength() {
        double absX = Math.abs(x);
        double absY = Math.abs(y);

        if(x == 0 && y == 0)
            return 0;

        return Math.sqrt(absX * absX + absY * absY);
    }

    public void normalize() {
        double length = getLength();

        if(x != 0)
            x = x / length;
        if(y != 0)
            y = y / length;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void damper(double damper) {
        x *= damper;
        y *= damper;

        if(x < 0.2 && x > -0.2) {
            x = 0;
        }

        if(y < 0.2 && y > -0.2) {
            y = 0;
        }
    }

    public Vector2 multiply(double factor) {
        Vector2 multipliedVector = new Vector2();
        multipliedVector.setX(x * factor);
        multipliedVector.setY(y * factor);

        return multipliedVector;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Vector2(x: %f, y: %f)", x, y);
    }
}
