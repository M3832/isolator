package com.isolator.game.ai.states;

public class AIDance extends AIStand {
    public AIDance(int standUntil) {
        super(standUntil);
    }

    @Override
    public String getAnimationName() { return "dance"; }
}
