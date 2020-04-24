package com.isolator.game.ai.states;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.core.Position;
import com.isolator.game.entity.Visitor;

public class AIWander extends AIState {

    public static int TIMEOUT_TIME = 60 * 10;

    Position targetPosition;
    private boolean done;
    private int updatesAlive;

    public AIWander(Position targetPosition) {
        this.targetPosition = targetPosition;
        updatesAlive = 0;
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        moveToTargetPosition(controlledEntity);
        updatesAlive++;

        if(targetPosition.isWithinRangeOf(20, controlledEntity.getPosition())) {
            ((AIController) controlledEntity.getController()).stop();
            done = true;
        }

        if(isTimeoutReached()) {
            done = true;
        }
    }

    @Override
    public boolean isReadyToTransition() {
        return done;
    }

    @Override
    public boolean isTimeoutReached() {
        return updatesAlive >= TIMEOUT_TIME;
    }

    private void moveToTargetPosition(Visitor controlledEntity) {
        AIController aiController = (AIController) controlledEntity.getController();
        Position aiPosition = controlledEntity.getPosition();

        boolean movingLeft = aiPosition.getX() > targetPosition.getX();
        boolean movingRight = aiPosition.getX() < targetPosition.getX();
        boolean movingUp = aiPosition.getY() > targetPosition.getY();
        boolean movingDown = aiPosition.getY() < targetPosition.getY();

        aiController.setLeft(movingLeft);
        aiController.setRight(movingRight);
        aiController.setUp(movingUp);
        aiController.setDown(movingDown);
    }
}
