package com.isolator.game.entity;

import com.isolator.engine.state.GameState;
import com.isolator.game.controller.Controller;
import com.isolator.engine.core.Size;
import com.isolator.engine.core.Vector2;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.states.IsolatorGameState;

import java.util.Comparator;
import java.util.Optional;

public class Player extends BaseEntity {

    private Visitor markedVisitor;

    public Player(Controller controller) {
        super(controller);
        this.collisionBoxSize = new Size(24, 32);
    }

    public void handleCollision(IsolatorGameState state, BaseObject other) {
        super.handleCollision(state, other);

        if(other instanceof BaseEntity && isFacing(other.getCollisionBox().getPosition())) {
            BaseEntity entity = (BaseEntity) other;
            Vector2 direction = Vector2.directionBetweenPositions(getPosition(), other.getPosition());
            Vector2 normalizedDirection = direction.normalized();
            entity.push(normalizedDirection, -getCurrentSpeed());
        }
    }

    @Override
    public void update(GameState state) {
        super.update((IsolatorGameState) state);
        removeMark();
        markClosestEntity(state);

        if(controller.isRequestingAction()) {
            isolateMark((IsolatorGameState) state);
        }
    }

    private void isolateMark(IsolatorGameState state) {
        if(markedVisitor != null) {
            markedVisitor.isolate(state);
        }
    }

    private void markClosestEntity(GameState state) {
        Optional<Visitor> closestVisitor = ((IsolatorGameState)state).getStreamOfVisitors()
                .filter(v -> v.getPosition().isWithinRangeOf(64, position))
                .filter(v -> isFacing(v.getPosition()))
                .filter(v -> !v.isIsolated())
                .min(Comparator.comparingDouble(e -> position.distanceTo(e.getPosition())));

        if(closestVisitor.isPresent()) {
            setMark(closestVisitor.get());
        }
    }

    private void setMark(Visitor visitor) {
        visitor.focus();
        markedVisitor = visitor;
    }

    private void removeMark() {
        if(markedVisitor != null) {
            markedVisitor.removeFocus();
        }
    }
}
