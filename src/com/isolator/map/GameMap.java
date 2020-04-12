package com.isolator.map;

import com.isolator.core.Size;
import com.isolator.game.GameState;

import java.awt.*;
import java.util.Arrays;

public class GameMap {

    private BaseTile[][] tiles;
    private Size cellSize;

    public GameMap(int xTiles, int yTiles, Size cellSize) {
        tiles = new BaseTile[xTiles][yTiles];
        this.cellSize = cellSize;
        Arrays.stream(tiles)
                .forEach(row -> Arrays.fill(row, new WoodFloor(cellSize)));
    }

    public BaseTile[][] getTiles() {
        return tiles;
    }

    public Size getCellSize() {
        return cellSize;
    }
}
