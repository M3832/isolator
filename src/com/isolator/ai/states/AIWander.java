package com.isolator.ai.states;

import com.isolator.controller.AIController;
import com.isolator.core.Position;
import com.isolator.entity.Visitor;
import com.isolator.game.GameState;

import java.util.Optional;

public class AIWander extends AIState {

    Optional<Position> targetPosition;
    private boolean done;

    public AIWander() {
        targetPosition = Optional.empty();
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        if(!targetPosition.isPresent()) {
            Position target;
            do {
                target = state.getMap().randomLocation();
            }  while(target.isWithinInteractionRange(controlledEntity.getPosition()));

            targetPosition = Optional.of(target);
            System.out.println(controlledEntity + " - New target position: " + targetPosition.get() + " Current position: " +controlledEntity.getPosition());
            return;
        }

        moveToTargetPosition(controlledEntity);

        if(targetPosition.get().isWithinInteractionRange(controlledEntity.getPosition())) {
            targetPosition = Optional.empty();
            done = true;
        }
    }

    @Override
    public boolean readyToTransition() {
        return done;
    }

    private void moveToTargetPosition(Visitor controlledEntity) {
        AIController aiController = (AIController) controlledEntity.getController();
        Position aiPosition = controlledEntity.getPosition();
        Position target = targetPosition.get();

        aiController.setLeft(aiPosition.getX() > target.getX());
        aiController.setRight(aiPosition.getX() < target.getX());
        aiController.setUp(aiPosition.getY() > target.getY());
        aiController.setDown(aiPosition.getY() < target.getY());
    }
}
