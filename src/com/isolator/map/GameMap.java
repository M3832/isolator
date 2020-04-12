package com.isolator.map;

import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.display.Camera;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private GridCell[][] gridCells;
    private Size cellSize;
    private Size mapSize;

    public GameMap(int xTiles, int yTiles, Size cellSize) {
        gridCells = new GridCell[xTiles][yTiles];
        this.cellSize = cellSize;
        fillWithTiles();
        addWallsToPerimeter();
        mapSize = new Size(xTiles * cellSize.getWidth(), yTiles * cellSize.getHeight());
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
                if(x == 0 || y == 0 || x == gridCells.length - 1 || y == gridCells[0].length - 1) {
                    gridCells[x][y].setTile(new Wall(cellSize));
                }
            }
        }
    }

    public List<GridCell> getUnwalkableGridCells(Camera camera) {
        Position viewableStart = getViewableStartingPosition(camera);
        Position viewableEnd = getViewableEndingPosition(camera);

        List<GridCell> unwalkableCells = new ArrayList<>();
        for(int x = viewableStart.getX(); x < Math.min(gridCells.length, viewableEnd.getX()); x++) {
            for (int y = viewableStart.getY(); y < Math.min(gridCells[0].length, viewableEnd.getY()); y++) {
                if(!gridCells[x][y].isWalkable()) {
                    unwalkableCells.add(gridCells[x][y]);
                }
            }
        }

        return unwalkableCells;
    }

    public Position getViewableStartingPosition(Camera camera) {
        return new Position(
                camera.getPosition().getX() / cellSize.getWidth(),
                camera.getPosition().getY() / cellSize.getHeight()
        );
    }

    public Position getViewableEndingPosition(Camera camera) {
        int extra = 2;
        return new Position(
                camera.getPosition().getX() / cellSize.getWidth() + camera.getSize().getWidth() / cellSize.getWidth() + extra,
                camera.getPosition().getY() / cellSize.getHeight() + camera.getSize().getHeight() / cellSize.getHeight() + extra
        );
    }

    public GridCell[][] getGridCells() {
        return gridCells;
    }

    public Size getCellSize() {
        return cellSize;
    }

    public Size getSize() {
        return mapSize;
    }

    public Position randomLocation() {
        int x = (int) (Math.random() * gridCells.length);
        int y = (int) (Math.random() * gridCells[0].length);

        if(!gridCells[x][y].isWalkable()) {
            return randomLocation();
        }

        return new Position(x * cellSize.getWidth(), y * cellSize.getHeight());
    }
}
