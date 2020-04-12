package com.isolator.map;

import com.isolator.core.Size;

import java.util.Arrays;

public class GameMap {

    private BaseTile[][] tiles;
    private Size cellSize;

    public GameMap(int xTiles, int yTiles, Size cellSize) {
        tiles = new BaseTile[xTiles][yTiles];
        this.cellSize = cellSize;
        Arrays.stream(tiles)
                .forEach(row -> Arrays.fill(row, new WoodFloor(cellSize)));

        for(int x = 0; x < tiles.length; x++) {
            for(int y = 0; y < tiles[0].length; y++) {
                if(x == 0 || y == 0) {
                    tiles[x][y] = new Wall(cellSize);
                }
            }
        }
    }

    public BaseTile[][] getTiles() {
        return tiles;
    }

    public Size getCellSize() {
        return cellSize;
    }
}
