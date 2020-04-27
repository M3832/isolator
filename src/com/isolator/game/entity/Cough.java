package com.isolator.game.entity;

import com.isolator.engine.game.GameState;
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
        IsolatorGameState isolatorState = (IsolatorGameState) state;

        isolatorState.getStreamOfVisitors()
                .filter((Visitor v) -> v.isWithinRangeOf(96, position))
                .forEach((Visitor v) -> v.exposeToVirus((IsolatorGameState) state));

        this.remove = true;
    }
}
