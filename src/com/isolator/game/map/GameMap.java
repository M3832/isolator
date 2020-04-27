package com.isolator.game.map;

import com.isolator.engine.game.GameScene;
import com.isolator.engine.game.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.game.entity.PathBlocker;
import com.isolator.engine.gfx.ImageUtils;
import com.isolator.engine.gfx.SpritesLibrary;
import com.isolator.game.IsolatorGameState;


import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameMap extends GameScene {

    private final GridCell[][] gridCells;
    private final Size cellSize;
    private final Size mapSize;
    private Shape walkableArea;

    public GameMap(int xTiles, int yTiles, Size cellSize) {
        gridCells = new GridCell[xTiles][yTiles];
        this.cellSize = cellSize;
        fillWithTiles();
        mapSize = new Size(xTiles * cellSize.getWidth(), yTiles * cellSize.getHeight());
    }

    private void fillWithTiles() {
        for(int x = 0; x < gridCells.length; x++) {
            for(int y = 0; y < gridCells[0].length; y++) {
                Position position = new Position(x * cellSize.getWidth(), y * cellSize.getHeight());
                gridCells[x][y] = new GridCell(
                        SpritesLibrary.WOOD_FLOOR,
                        position,
                        cellSize);
            }
        }
    }

    public void addWallsToPerimeter(IsolatorGameState state) {
        for(int x = 0; x < gridCells.length; x++) {
            for(int y = 0; y < gridCells[0].length; y++) {
                if(x == 0 || y == 0 || x == gridCells.length - 1 || y == gridCells[0].length - 1) {
                    PathBlocker pathBlocker = new PathBlocker(new Position(x * cellSize.getWidth(), y * cellSize.getHeight()), cellSize);
                    if(y == 0) {
                        gridCells[x][y].setTileSprite(SpritesLibrary.WOOD_WALL_N);
                    } else {
                        gridCells[x][y].setTileSprite(SpritesLibrary.WOOD_WALL);
                    }

                    if(!(y == gridCells[0].length - 1 && x == gridCells.length / 2)) {
                        state.addObject(pathBlocker);
                    } else {
                        gridCells[x][y].setTileSprite(SpritesLibrary.DOOR);
                    }
                }
            }
        }
        addStage(state);
        calculateWalkableArea(state);
    }

    private void calculateWalkableArea(IsolatorGameState state) {
        List<Position> points = new ArrayList<>();

        for(int x = 0; x < gridCells.length; x++) {
            for(int y = 0; y < gridCells[0].length; y++) {
                if(!state.checkCollision(CollisionBox.of(new Position(x * cellSize.getWidth(), y * cellSize.getHeight()), cellSize))
                        && !allNeighborsFree(new Position(x, y), state)) {
                    points.add(new Position(x * cellSize.getWidth() + cellSize.getWidth()/2, y * cellSize.getHeight() + cellSize.getHeight()/2));
                }
            }
        }

        List<Position> sorted = sortShapeVertices(points);
        List<Position> simplified = simplifyShape(sorted);
        int[] xs = simplified.stream().mapToInt(Position::getX).toArray();
        int[] ys = simplified.stream().mapToInt(Position::getY).toArray();
        walkableArea = new Polygon(xs, ys, simplified.size());
    }

    private List<Position> simplifyShape(List<Position> sorted) {
        List<Position> simplifiedShape = new ArrayList<>();
        simplifiedShape.add(sorted.get(0));
        for(int i = 1; i < sorted.size() - 1; i++) {
            Position previous = sorted.get(i-1);
            Position current = sorted.get(i);
            Position next = sorted.get(i+1);
            if(previous.getY() == current.getY() && next.getY() != current.getY() || previous.getX() == current.getX() && next.getX() != current.getX()) {
                simplifiedShape.add(current);
            }
        }

        return simplifiedShape;
    }

    private boolean allNeighborsFree(Position position, IsolatorGameState state) {
        for(int x = position.getX() - 1; x < position.getX() + 2; x++) {
            for(int y = position.getY() - 1; y < position.getY() + 2; y++) {
                if(new Position(x, y).equals(position)) {
                    continue;
                }
                if(state.checkCollision(CollisionBox.of(new Position(x * cellSize.getWidth(), y * cellSize.getHeight()), cellSize))) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<Position> sortShapeVertices(List<Position> points) {
        List<Position> sortedPositions = new ArrayList<>();
        List<Position> open = new ArrayList<>(points);

        while(!open.isEmpty()) {
            Position current = open.get(0);
            open.remove(current);
            sortedPositions.add(current);
            open.sort(Comparator.comparingDouble(p -> current.distanceTo(p)));
        }

        return sortedPositions;
    }

    private void addStage(IsolatorGameState state) {
        int stageXStart =  gridCells.length - gridCells.length/2 - 5;
        int stageXEnd = 10;
        int stageYEnd = 6;
        for(int x = 0; x < stageXEnd; x++) {
            for(int y = 0; y < stageYEnd; y++) {
                state.addObject(new PathBlocker(
                        new Position(
                                (stageXStart + x) * cellSize.getWidth(),
                                y * cellSize.getHeight()),
                        cellSize));
                if(x == 0 || x == stageXEnd - 1 || y == 0 || y == stageYEnd - 1) {
                    gridCells[stageXStart + x][y].setTileSprite(SpritesLibrary.WOOD_WALL_N);
                } else {
                    gridCells[stageXStart + x][y].setTileSprite(SpritesLibrary.STAGE_FLOOR);
                }
                if(y == 0 || ((x == 0 || x == stageXEnd - 1) && !(y == stageYEnd - 1))) {
                    gridCells[stageXStart + x][y].setTileSprite(SpritesLibrary.STAGE_FLOOR);
                }
            }
        }
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

    public Size getSize() {
        return mapSize;
    }

    public Position getRandomAvailableLocation(IsolatorGameState state) {
        Position randomPosition = new Position(
                (int) (state.getRandomGenerator().nextDouble() * gridCells.length * cellSize.getWidth()),
                (int) (state.getRandomGenerator().nextDouble() * gridCells[0].length * cellSize.getHeight())
        );

        if(!walkableArea.contains(new Rectangle(randomPosition.getX(), randomPosition.getY(), cellSize.getWidth(), cellSize.getHeight()))) {
            return getRandomAvailableLocation(state);
        }

        return randomPosition;
    }

    @Override
    public Size getSceneSize() {
        return mapSize;
    }

    @Override
    public Image getSceneGraphics(GameState state) {
        Camera camera = state.getCamera();
        Position startRenderingPosition = getViewableStartingPosition(camera);
        Position endRenderingPosition = getViewableEndingPosition(camera);
        Image renderedScene = ImageUtils.createCompatibleImage(camera.getSize(), ImageUtils.ALPHA_OPAQUE);
        Graphics2D sceneGraphics = (Graphics2D) renderedScene.getGraphics();

        for(int x = startRenderingPosition.getX(); x < Math.min(gridCells.length, endRenderingPosition.getX()); x++) {
            for(int y = startRenderingPosition.getY(); y < Math.min(gridCells[0].length, endRenderingPosition.getY()); y++) {
                sceneGraphics.drawImage(
                        gridCells[x][y].getTileSprite(),
                        x * cellSize.getWidth() - camera.getPosition().getX(),
                        y * cellSize.getHeight() - camera.getPosition().getY(),
                        null
                );
            }
        }

        sceneGraphics.dispose();
        return renderedScene;
    }

    public Shape getWalkableArea() {
        return walkableArea;
    }
}
