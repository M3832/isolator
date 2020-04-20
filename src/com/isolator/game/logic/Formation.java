package com.isolator.game.logic;

import com.isolator.engine.core.Position;

import java.util.List;

public interface Formation {
    List<Position> calculatePositions(double length, int numberOfPositions);
}
