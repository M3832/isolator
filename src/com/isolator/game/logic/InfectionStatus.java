package com.isolator.game.logic;

import com.isolator.game.IsolatorGameState;
import com.isolator.game.entity.Visitor;

import java.util.Random;

public class InfectionStatus {
    public enum Status {
        NEGATIVE, INFECTED, SICK;
    }

    private Status status;

    public InfectionStatus() {
        this(Status.NEGATIVE);
    }

    public InfectionStatus(Status status) {
        this.status = status;
    }

    public void update(IsolatorGameState state, Visitor visitor) {
        if(status.equals(Status.SICK)) {
            double riskOfCoughing = state.getRandomGenerator().nextDouble();
            if(riskOfCoughing < 0.1) {
                visitor.cough(state);
            }
        }
    }

    public void exposeWithRisk(Random random, double risk) {
        boolean infect = random.nextDouble() < risk;

        if(infect) {
            this.status = Status.INFECTED;
        }
    }

    public boolean isInfected() {
        return status.equals(Status.INFECTED);
    }

    public boolean isSick() {
        return status.equals(Status.SICK);
    }

    public boolean isWell() {
        return status.equals(Status.NEGATIVE);
    }
}
