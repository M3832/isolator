package com.isolator.game.ai.states;

import com.isolator.engine.GameState;
import com.isolator.game.objects.BaseEntity;
import com.isolator.game.objects.Visitor;
import com.isolator.game.IsolatorGameState;
import com.isolator.engine.ui.UIBase;
import com.isolator.engine.ui.UIContainer;
import com.isolator.engine.ui.UIText;

public class AIStand extends AIState {

    private long startTime;
    private long standUntil;
    private boolean done;

    public AIStand() {
        this((int)(Math.random() * 10));
    }

    public AIStand(long durationInSeconds) {
        startTime = System.currentTimeMillis();
        standUntil = durationInSeconds * 1000 + startTime;
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        if((getScaledEndTime(state) - System.currentTimeMillis()) <= 0) {
            done = true;
        }
    }

    @Override
    public boolean readyToTransition() {
        return done;
    }

    public long getScaledEndTime(GameState state) {
        long standTime = standUntil - startTime;
        long scaledStandTime = (long) (standTime / state.getGameSpeed());
        return startTime + scaledStandTime;
    }

    @Override
    public UIBase getDebugUI(GameState state, BaseEntity entity) {
        UIContainer container = new UIContainer();
        container.addElement(new UIText(this.getClass().getSimpleName()));
        container.addElement(new UIText("Time left: " + (getScaledEndTime(state) - System.currentTimeMillis())));
        return container;
    }
}
