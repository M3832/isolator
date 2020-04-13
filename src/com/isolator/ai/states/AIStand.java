package com.isolator.ai.states;

import com.isolator.entity.BaseEntity;
import com.isolator.entity.Visitor;
import com.isolator.game.GameState;
import com.isolator.ui.UIBase;
import com.isolator.ui.UIContainer;
import com.isolator.ui.UIText;

public class AIStand extends AIState {

    private long startTime;
    private long standUntil;
    private boolean done;

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        if(standUntil == 0) {
            startTime = System.currentTimeMillis();
            long waitSeconds = ((int)(Math.random() * 10));
            standUntil = waitSeconds * 1000 + startTime;
        }

        if((getScaledEndTime(state) - System.currentTimeMillis()) <= 0) {
            done = true;
        }
    }

    @Override
    public boolean readyToTransition() {
        return done;
    }

    @Override
    public UIBase getDebugUI(GameState state, BaseEntity entity) {
        UIContainer container = new UIContainer();
        container.addElement(new UIText(this.getClass().getSimpleName()));
        container.addElement(new UIText("Time left: " + (getScaledEndTime(state) - System.currentTimeMillis())));
        return container;
    }

    public long getScaledEndTime(GameState state) {
        long standTime = standUntil - startTime;
        long scaledStandTime = (long) (standTime / state.getGameSpeed());
        return startTime + scaledStandTime;
    }
}
