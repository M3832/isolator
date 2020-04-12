package com.isolator.map;

import com.isolator.core.Position;
import com.isolator.core.Size;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameMap {

    private GridCell[][] gridCells;
    private Size cellSize;

    public GameMap(int xTiles, int yTiles, Size cellSize) {
        gridCells = new GridCell[xTiles][yTiles];
        this.cellSize = cellSize;
        fillWithTiles();
        addWallsToPerimeter();
    }

    private void fillWithTiles() {
        for(int x = 0; x < gridCells.length; x++) {
            for(int y = 0; y < gridCells[0].length; y++) {
                gridCells[x][y] = new GridCell(
                        new WoodFloor(cellSize),
                        new Position(x * cellSize.getWidth(), y * cellSize.getHeight()),
                        cellSize);
            }
        }
    }

    private void addWallsToPerimeter() {
        for(int x = 0; x < gridCells.length; x++) {
            for(int y = 0; y < gridCells[0].length; y++) {
                if(x == 0 || y == 0) {
                    gridCells[x][y].setTile(new Wall(cellSize));
                }
            }
        }
    }

    public List<GridCell> getUnwalkableGridCells() {
        return Arrays.stream(gridCells)
                .flatMap(row -> Arrays.stream(row))
                .filter(tile -> !tile.isWalkable())
                .collect(Collectors.toList());
    }

    public GridCell[][] getGridCells() {
        return gridCells;
    }

    public Size getCellSize() {
        return cellSize;
    }
}
