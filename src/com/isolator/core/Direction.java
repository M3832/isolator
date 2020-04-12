package com.isolator.core;

public enum Direction {
    S, SW, W, NW, N, NE, E, SE;

    public static Direction fromVelocity(Velocity velocity) {
        return fromVelocity(velocity, Direction.N);
    }

    public static Direction fromVelocity(Velocity velocity, Direction direction) {
        double velocityX = velocity.getVelocityX();
        double velocityY = velocity.getVelocityY();

        if(velocityX == 0 && velocityY == 0) return direction;

        if(velocityX == 0 && velocityY > 0) return S;
        if(velocityX < 0 && velocityY > 0) return SW;
        if(velocityX < 0 && velocityY == 0) return W;
        if(velocityX < 0 && velocityY < 0) return NW;
        if(velocityX == 0 && velocityY < 0) return N;
        if(velocityX > 0 && velocityY < 0) return NE;
        if(velocityX > 0 && velocityY == 0) return E;
        if(velocityX > 0 && velocityY > 0) return SE;

        return direction;
    }
}
