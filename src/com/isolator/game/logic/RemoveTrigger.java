package com.isolator.game.logic;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.state.GameState;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.trigger.Trigger;
import com.isolator.game.entity.Visitor;

public class RemoveTrigger extends Trigger {

    public RemoveTrigger(Position position, Size size) {
        super(position, size);
    }

    @Override
    protected void onEnter(GameState state, BaseObject o) {
        if(o instanceof Visitor) {
            o.remove();
        }
    }

}
