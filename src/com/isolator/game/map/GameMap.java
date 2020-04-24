package com.isolator.game.map;

import com.isolator.engine.GameScene;
import com.isolator.engine.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.gameobjects.Blocker;
import com.isolator.engine.gfx.ImageUtils;
import com.isolator.engine.gfx.SpritesLibrary;
import com.isolator.game.IsolatorGameState;
import com.isolator.game.entity.BaseEntity;

import java.awt.*;

import java.util.List;
import java.util.stream.Collectors;

public class GameMap extends GameScene {

    private final GridCell[][] gridCells;
    private final Size cellSize;
    private final Size mapSize;

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
                    Blocker blocker = new Blocker(new Position(x * cellSize.getWidth(), y * cellSize.getHeight()), cellSize);
                    gridCells[x][y].setTileSprite(SpritesLibrary.WOOD_WALL_N);
                    state.addObject(blocker);
                }
            }
        }
        addStage(state);
    }

    private void addStage(IsolatorGameState state) {
        int stageXStart =  gridCells.length - gridCells.length/2 - 5;
        int stageXEnd = 10;
        int stageYEnd = 6;
        for(int x = 0; x < stageXEnd; x++) {
            for(int y = 0; y < stageYEnd; y++) {
                if(x == 0 || x == stageXEnd - 1 || y == 0 || y == stageYEnd - 1) {
                    gridCells[stageXStart + x][y].setTileSprite(SpritesLibrary.WOOD_WALL_N);
                    state.addObject(new Blocker(
                            new Position(
                                    (stageXStart + x) * cellSize.getWidth(),
                                    y * cellSize.getHeight()),
                            cellSize));
                } else {
                    gridCells[stageXStart + x][y].setTileSprite(SpritesLibrary.STAGE_FLOOR);
                }
                if(y == 0) {
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

    public Position getRandomAvailableLocation(IsolatorGameState state, Size collisionBoxSize) {
        Position randomPosition = new Position(
                (int) (state.getRandomGenerator().nextDouble() * gridCells.length * cellSize.getWidth()),
                (int) (state.getRandomGenerator().nextDouble() * gridCells[0].length * cellSize.getHeight())
        );

        CollisionBox targetCollisionBox = CollisionBox.of(randomPosition, collisionBoxSize);

        for(BaseObject object : state.getObjects().stream().filter(object -> !(object instanceof BaseEntity)).collect(Collectors.toList())) {
            if (object.getPosition().isWithinRangeOf(128, randomPosition)
                    && object.getCollisionBox().checkCollision(targetCollisionBox)) {
                return getRandomAvailableLocation(state, collisionBoxSize);
            }
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
}
