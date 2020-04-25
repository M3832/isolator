package com.isolator.engine.core;

public class Vector2 {

    private double x;
    private double y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 directionBetweenPositions(Position startingPosition, Position terminalPosition) {
        Vector2 directionBetweenPositions = new Vector2();
        directionBetweenPositions.setX(startingPosition.getX() - terminalPosition.getX());
        directionBetweenPositions.setY(startingPosition.getY() - terminalPosition.getY());

        return directionBetweenPositions;
    }

    public static double dotProduct(Vector2 v1, Vector2 v2) {
        Vector2 normalized1 = v1.normalized();
        Vector2 normalized2 = v2.normalized();
        return normalized1.getX() * normalized2.getX() + normalized1.getY() * normalized2.getY();
    }

    private double getLength() {
        if(x == 0 && y == 0)
            return 0;

        double absX = Math.abs(x);
        double absY = Math.abs(y);
        return Math.sqrt(absX * absX + absY * absY);
    }

    public Vector2 normalized() {
        double length = getLength();
        double newX = x;
        double newY = y;
        if(x != 0)
            newX = x / length;
        if(y != 0)
            newY = y / length;

        return new Vector2(newX, newY);
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

    public void rotate(int angle) {
        double newX = Math.cos(angle) * x - Math.sin(angle) * y;
        double newY = Math.sin(angle) * x + Math.cos(angle) * y;

        this.x = newX;
        this.y = newY;
    }
}
