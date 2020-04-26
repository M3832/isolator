package com.isolator.game.ai.states;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.core.Position;
import com.isolator.game.entity.Visitor;

import java.util.List;

public class AIWander extends AIState {

    public static int TIMEOUT_TIME = 60 * 20;

    private List<Position> wanderPath;
    private boolean done;
    private int updatesAlive;
    private int range;

    public AIWander(List<Position> wanderPath) {
        this.wanderPath = wanderPath;
        updatesAlive = 0;
        range = 20;
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        updatesAlive++;
        if(!wanderPath.isEmpty()) {
            moveToTargetPosition(controlledEntity);
            boolean withinRangeOf = getCurrentTarget().isWithinRangeOf(range, controlledEntity.getPosition());
            if(withinRangeOf) {
                wanderPath.remove(0);
            }

            if(wanderPath.isEmpty()) {
                ((AIController) controlledEntity.getController()).stop();
                done = true;
            }
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
        double deltaX = aiPosition.distanceTo(new Position(getCurrentTarget().getX(), aiPosition.getY()));
        double deltaY = aiPosition.distanceTo(new Position(aiPosition.getX(), getCurrentTarget().getY()));

        boolean movingLeft = aiPosition.getX() > getCurrentTarget().getX() && deltaX > range;
        boolean movingRight = aiPosition.getX() < getCurrentTarget().getX() && deltaX > range;
        boolean movingUp = aiPosition.getY() > getCurrentTarget().getY() && deltaY > range;
        boolean movingDown = aiPosition.getY() < getCurrentTarget().getY() && deltaY > range;

        aiController.setLeft(movingLeft);
        aiController.setRight(movingRight);
        aiController.setUp(movingUp);
        aiController.setDown(movingDown);
    }

    private Position getCurrentTarget() {
        return wanderPath.get(0);
    }

    public List<Position> getPositions() {
        return wanderPath;
    }
}
