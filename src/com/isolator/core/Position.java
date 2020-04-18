package com.isolator.core;

public class Position {

    int x, y;

    public Position() {
        this(0, 0);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static boolean withinCloseProximity(Position position, Position position1) {
        double x = position.getX() - position1.getX();
        double y = position.getY() - position1.getY();

        return Math.abs(x * y) > 20;
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

    public boolean isWithinInteractionRange(Position other) {
        int interactionRange = 20;
        int deltaX = Math.max(Math.abs(other.getX()), Math.abs(x)) - Math.min(Math.abs(other.getX()), Math.abs(x));
        int deltaY = Math.max(Math.abs(other.getY()), Math.abs(y)) - Math.min(Math.abs(other.getY()), Math.abs(y));

        return deltaX <= interactionRange && deltaY <= interactionRange;
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
}
