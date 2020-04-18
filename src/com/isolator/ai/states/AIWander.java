package com.isolator.ai.states;

import com.isolator.controller.AIController;
import com.isolator.core.Position;
import com.isolator.entity.BaseEntity;
import com.isolator.entity.Visitor;
import com.isolator.game.GameState;
import com.isolator.ui.UIBase;
import com.isolator.ui.UIContainer;
import com.isolator.ui.UIText;

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
                target = state.getMap().getRandomAvailableLocation(controlledEntity.getCollisionBoxSize());
            }  while(target.isWithinInteractionRange(controlledEntity.getPosition()));

            targetPosition = Optional.of(target);
            return;
        }

        moveToTargetPosition(controlledEntity);

        if(targetPosition.get().isWithinInteractionRange(controlledEntity.getPosition())) {
            targetPosition = Optional.empty();
            ((AIController) controlledEntity.getController()).stop();
            done = true;
        }
    }

    @Override
    public boolean readyToTransition() {
        return done;
    }

    @Override
    public UIBase getDebugUI(GameState state, BaseEntity baseEntity) {
        UIContainer container = new UIContainer();
        container.addElement(new UIText(this.getClass().getSimpleName(), 18));
        container.addElement(new UIText("Current position: " + baseEntity.getPosition(), 10));
        container.addElement(new UIText("Target position: " + targetPosition, 10));

        return container;
    }

    private void moveToTargetPosition(Visitor controlledEntity) {
        AIController aiController = (AIController) controlledEntity.getController();
        Position aiPosition = controlledEntity.getPosition();
        Position target = targetPosition.get();

        boolean movingLeft = aiPosition.getX() > target.getX();
        boolean movingRight = aiPosition.getX() < target.getX();
        boolean movingUp = aiPosition.getY() > target.getY();
        boolean movingDown = aiPosition.getY() < target.getY();

        aiController.setLeft(movingLeft);
        aiController.setRight(movingRight);
        aiController.setUp(movingUp);
        aiController.setDown(movingDown);
    }
}
