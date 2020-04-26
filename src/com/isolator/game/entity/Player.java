package com.isolator.game.entity;

import com.isolator.engine.game.GameState;
import com.isolator.engine.controller.Controller;
import com.isolator.engine.core.Size;
import com.isolator.engine.core.Vector2;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.IsolatorGameState;

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

        if(controller.isRequestingDebugMode()) {
            state.toggleDebugMode();
        }

        if(controller.isRequestingAction()) {
            isolateMark();
        }

        if(controller.isRequestingSpeedUp()) {
            state.increaseGameSpeed();
        }

        if(controller.isRequestingSlowDown()) {
            state.decreaseGameSpeed();
        }
    }

    private void isolateMark() {
        if(markedVisitor != null) {
            markedVisitor.isolate();
        }
    }

    private void markClosestEntity(GameState state) {
        Optional<Visitor> closestVisitor = state.getObjects().stream()
                .filter(o -> o instanceof Visitor)
                .map(o -> (Visitor) o)
                .filter(v -> v.getPosition().isWithinRangeOf(64, position))
                .filter(v -> isFacing(v.getPosition()))
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
