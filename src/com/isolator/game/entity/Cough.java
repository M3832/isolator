package com.isolator.game.entity;

import com.isolator.engine.GameState;
import com.isolator.engine.core.Position;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.IsolatorGameState;

public class Cough extends BaseObject {

    public Cough(Position position) {
        super();
        this.position = position;
    }

    @Override
    public void update(GameState state) {
        state.getObjects().stream()
                .filter(o -> o.getPosition().isWithinRangeOf(96, position))
                .filter(o -> o instanceof Visitor)
                .map(o -> (Visitor) o)
                .forEach(v -> v.exposeToVirus((IsolatorGameState) state));

        this.remove = true;
    }
}
