package com.isolator.game.logic;

import com.isolator.game.IsolatorGameState;
import com.isolator.game.entity.Visitor;

import java.util.Random;

public class InfectionStatus {
    public enum Status {
        NEGATIVE, INFECTED, SICK, ISOLATED;
    }

    private Status status;
    private int incubationTime;

    public InfectionStatus() {
        this(Status.NEGATIVE);
        incubationTime = 3000;
    }

    public InfectionStatus(Status status) {
        this.status = status;
    }

    public void update(IsolatorGameState state, Visitor visitor) {
        if(status.equals(Status.SICK)) {
            handleCough(state, visitor);
        } else if (status.equals(Status.INFECTED)) {
            handleIncubation();
        }
    }

    private void handleIncubation() {
        if(--incubationTime == 0) {
            this.status = Status.SICK;
        }
    }

    private void handleCough(IsolatorGameState state, Visitor visitor) {
        double riskOfCoughing = state.getRandomGenerator().nextDouble();
        if(riskOfCoughing < 0.01) {
            visitor.cough(state);
        }
    }

    public void exposeWithRisk(Random random, double risk) {
        boolean infect = random.nextDouble() < risk;

        if(infect && this.status.equals(Status.NEGATIVE)) {
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

    public boolean isIsolated() {
        return status.equals(Status.ISOLATED);
    }
}
