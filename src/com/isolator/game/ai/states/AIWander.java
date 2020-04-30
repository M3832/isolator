package com.isolator.game.ai.states;

import com.isolator.engine.state.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.core.Position;
import com.isolator.game.entity.Visitor;

import java.util.List;

public class AIWander extends AIState {


    private List<Position> wanderPath;
    private boolean done;
    private int range;

    public AIWander(List<Position> wanderPath) {
        this.wanderPath = wanderPath;
        range = 10;
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        if(!wanderPath.isEmpty()) {
            moveToTargetPosition(controlledEntity);
            boolean withinRangeOf = getCurrentTarget().isWithinRangeOf(range + 10, controlledEntity.getPosition());
            if(withinRangeOf) {
                wanderPath.remove(0);
            }

            if(wanderPath.isEmpty()) {
                ((AIController) controlledEntity.getController()).stop();
                done = true;
            }
        }
    }

    @Override
    public boolean isReadyToTransition() {
        return done;
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
}
