package com.isolator.engine.trigger;

import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.game.GameState;
import com.isolator.engine.gameobjects.BaseObject;

public abstract class Trigger extends BaseObject {

    public Trigger(Position position, Size size) {
        super();
        this.position = position;
        this.size = size;
    }

    @Override
    public void update(GameState state) {
        state.getObjects().stream()
                .filter(o -> o.getCollisionBox().checkCollision(getCollisionBox()))
                .filter(o -> !o.equals(this))
                .forEach(o -> onEnter(state, o));
    }

    public CollisionBox getCollisionBox() {
        return CollisionBox.of(position, size);
    }

    protected abstract void onEnter(GameState state, BaseObject o);
}
