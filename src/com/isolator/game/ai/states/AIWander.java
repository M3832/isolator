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

    public AIWander(List<Position> wanderPath) {
        this.wanderPath = wanderPath;
        updatesAlive = 0;
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        updatesAlive++;
        if(!wanderPath.isEmpty()) {
            moveToTargetPosition(controlledEntity);

            boolean withinRangeOf = wanderPath.get(0).isWithinRangeOf(10, controlledEntity.getPosition());
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

        boolean movingLeft = aiPosition.getX() > wanderPath.get(0).getX() && aiPosition.getX() > 0;
        boolean movingRight = aiPosition.getX() < wanderPath.get(0).getX();
        boolean movingUp = aiPosition.getY() > wanderPath.get(0).getY() && aiPosition.getY() > 0;
        boolean movingDown = aiPosition.getY() < wanderPath.get(0).getY();

        aiController.setLeft(movingLeft);
        aiController.setRight(movingRight);
        aiController.setUp(movingUp);
        aiController.setDown(movingDown);
    }

    public List<Position> getPositions() {
        return wanderPath;
    }
}
