package com.isolator.engine.core;

import java.util.Objects;

public class Position {

    int x, y;

    public Position() {
        this(0, 0);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y) {
        this((int) x, (int) y);
    }

    public static Position add(Position position, Position position1) {
        return new Position(position.getX() + position1.getX(), position.getY() + position1.getY());
    }

    public void apply(MovementMotor movementMotor) {
        Vector2 movement = movementMotor.getMovement();
        this.x += (int) movement.getX();
        this.y += (int) movement.getY();
    }

    public Position getNextPosition(MovementMotor movementMotor) {
        Position nextPosition = new Position(x, y);
        nextPosition.apply(movementMotor);
        return nextPosition;
    }

    public double distanceTo(Position other) {
        double deltaX = this.getX() - other.getX();
        double deltaY = this.getY() - other.getY();

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Override
    public boolean equals(Object that){
        if(!(that instanceof Position)) {
            return false;
        }
        Position thatPosition = (Position) that;
        return this.getX() == thatPosition.getX() && this.getY() == thatPosition.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(X: %d, Y: %d)", x, y);
    }

    public void subtract(Position other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public boolean isWithinRangeOf(double range, Position position) {
        return distanceTo(position) < range;
    }

    public boolean isLeftOf(Position other) {
        return x < other.getX();
    }

    public boolean isRightOf(Position other) {
        return x < other.getX();
    }

    public boolean isAbove(Position other) {
        return y < other.getY();
    }

    public boolean isUnder(Position other) {
        return y < other.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
