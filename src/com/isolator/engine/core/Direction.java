package com.isolator.engine.core;

public enum Direction {
    S(0),
    SW(1),
    W(2),
    NW(3),
    N(4),
    NE(5),
    E(6),
    SE(7);

    private static final double VELOCITY_SENSITIVITY_VALUE = 0.5;

    private int animationRow;

    Direction(int animationRow) {
        this.animationRow = animationRow;
    }

    public int getAnimationRow() {
        return animationRow;
    }

    public static Direction fromVelocity(Velocity velocity, Direction direction) {
        Vector2 directionVector = velocity.getDirection();
        double x = directionVector.getX();
        double y = directionVector.getY();

        if(x == 0 && y == 0) return direction;

        if(x == 0 && y > 0) return S;
        if(x < 0 && y == 0) return W;
        if(x == 0 && y < 0) return N;
        if(x > 0 && y == 0) return E;
        if(x < 0 && y > 0) return SW;
        if(x < 0 && y < 0) return NW;
        if(x > 0 && y < 0) return NE;
        if(x > 0 && y > 0) return SE;

        return direction;
    }
}
