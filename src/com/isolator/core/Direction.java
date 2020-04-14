package com.isolator.core;

public enum Direction {
    S(0),
    SW(1),
    W(2),
    NW(3),
    N(4),
    NE(5),
    E(6),
    SE(7);

    private int animationRow;

    private Direction(int animationRow) {
        this.animationRow = animationRow;
    }

    public int getAnimationRow() {
        return animationRow;
    }

    public static Direction fromVelocity(Velocity velocity) {
        return fromVelocity(velocity, Direction.N);
    }

    public static Direction fromVelocity(Velocity velocity, Direction direction) {
        double velocityX = velocity.getVelocityX();
        double velocityY = velocity.getVelocityY();

        if(velocityX == 0 && velocityY == 0) return direction;

        if(velocityX == 0 && velocityY > 0) return S;
        if(velocityX < 0 && velocityY == 0) return W;
        if(velocityX == 0 && velocityY < 0) return N;
        if(velocityX > 0 && velocityY == 0) return E;
        if(velocityX < 0 && velocityY > 0) return SW;
        if(velocityX < 0 && velocityY < 0) return NW;
        if(velocityX > 0 && velocityY < 0) return NE;
        if(velocityX > 0 && velocityY > 0) return SE;

        return direction;
    }
}
