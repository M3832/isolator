package com.isolator.engine.core;

import com.isolator.game.controller.Controller;

public enum Direction {
    S(0),
    SW(1),
    W(2),
    NW(3),
    N(4),
    NE(5),
    E(6),
    SE(7);

    private final int animationRow;

    Direction(int animationRow) {
        this.animationRow = animationRow;
    }

    public static Direction fromVector(Vector2 lookDirection) {
        double x = lookDirection.getX();
        double y = lookDirection.getY();

        if(x == 0 && y > 0) return S;
        if(x < 0 && y == 0) return W;
        if(x == 0 && y < 0) return N;
        if(x > 0 && y == 0) return E;
        if(x < 0 && y > 0) return SW;
        if(x < 0 && y < 0) return NW;
        if(x > 0 && y < 0) return NE;
        if(x > 0 && y > 0) return SE;

        return N;
    }

    public int getAnimationRow() {
        return animationRow;
    }

    public static Direction fromVelocity(MovementMotor movementMotor, Controller controller, Direction direction) {
        if(controller.isRequestingMove() && !(movementMotor.isBlocked())) {
            return fromVector(movementMotor.getDirection());
        }

        return direction;
    }
}
