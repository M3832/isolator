package com.isolator.game.logic;

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

    public boolean isInfected() {
        return status.equals(Status.INFECTED);
    }

    public boolean isSick() {
        return status.equals(Status.SICK);
    }

    public boolean isWell() {
        return status.equals(Status.NEGATIVE);
    }

    public void exposeWithRisk(Random random, double risk) {
        boolean infect = random.nextDouble() < risk;

        if(infect) {
            this.status = Status.INFECTED;
        }
    }
}
