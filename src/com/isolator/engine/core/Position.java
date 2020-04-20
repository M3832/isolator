package com.isolator.engine.core;

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

    public static boolean withinCloseProximity(Position position, Position position1) {
        double x = position.getX() - position1.getX();
        double y = position.getY() - position1.getY();

        return Math.abs(x) < 128 && Math.abs(y) < 128;
    }

    public static Position add(Position position, Position position1) {
        return new Position(position.getX() + position1.getX(), position.getY() + position1.getY());
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

    public void apply(Velocity velocity) {
        Vector2 movement = velocity.getMovement();
        this.x += (int) movement.getX();
        this.y += (int) movement.getY();
    }

    public Position getNextPosition(Velocity velocity) {
        Position nextPosition = new Position(x, y);
        nextPosition.apply(velocity);
        return nextPosition;
    }

    public double distanceTo(Position other) {
        double deltaX = this.getX() - other.getX();
        double deltaY = this.getY() - other.getY();

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public boolean isWithinInteractionRange(Position other) {
        int interactionRange = 128;
        return distanceTo(other) < interactionRange;
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
    public String toString() {
        return String.format("(X: %d, Y: %d)", x, y);
    }

    public void subtract(Position other) {
        this.x -= other.x;
        this.y -= other.y;
    }
}
