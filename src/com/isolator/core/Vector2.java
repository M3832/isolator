package com.isolator.core;

public class Vector2 {

    private double x;
    private double y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public static Vector2 directionBetweenPositions(Position startingPosition, Position terminalPosition) {
        Vector2 directionBetweenPositions = new Vector2();
        directionBetweenPositions.setX(startingPosition.getX() - terminalPosition.getX());
        directionBetweenPositions.setY(startingPosition.getY() - terminalPosition.getY());

        return directionBetweenPositions;
    }

    public static double dotProduct(Vector2 v1, Vector2 v2) {
        return v1.getX() * v2.getX() + v1.getY() + v2.getY();
    }

    private double getLength() {
        if(x == 0 && y == 0)
            return 0;

        double absX = Math.abs(x);
        double absY = Math.abs(y);
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

    public void add(Vector2 direction) {
        this.x += direction.getX();
        this.y += direction.getY();
    }
}
