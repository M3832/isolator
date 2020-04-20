package com.isolator.game.logic;

import com.isolator.engine.core.Position;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CircleFormation implements Formation {

    @Override
    public List<Position> calculatePositions(double radius, int numberOfPositions) {
        List<Position> positions = new LinkedList<>();
        double distance = 2 * Math.PI / numberOfPositions;

        for(int i = 0; i < numberOfPositions; i++) {
            double angle = distance * i;
            Position calculatedPos = new Position(Math.sin(angle) * radius, Math.cos(angle) * radius);
            positions.add(calculatedPos);
        }

        return positions;
    }
}
