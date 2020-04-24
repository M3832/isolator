package com.isolator.game.ai.states;

public class AICough extends AIStand {

    public AICough() {
        super(90);
    }

    @Override
    public String getAnimationName() { return "cough"; }
}
