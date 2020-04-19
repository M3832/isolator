package com.isolator.game.ai.states;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.core.Position;
import com.isolator.game.objects.BaseEntity;
import com.isolator.game.objects.Visitor;
import com.isolator.game.IsolatorGameState;
import com.isolator.engine.ui.UIBase;
import com.isolator.engine.ui.UIContainer;
import com.isolator.engine.ui.UIText;

import java.util.Optional;

public class AIWander extends AIState {

    Optional<Position> targetPosition;
    private boolean done;

    public AIWander() {
        targetPosition = Optional.empty();
    }

    public AIWander(Optional<Position> targetPosition) {
        this.targetPosition = targetPosition;
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        if(!targetPosition.isPresent() && state instanceof IsolatorGameState) {
            Position target;
            do {
                target = ((IsolatorGameState)state).getMap()
                        .getRandomAvailableLocation(state, controlledEntity.getCollisionBoxSize());
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
