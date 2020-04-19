package com.isolator.game.map;

import com.isolator.engine.GameScene;
import com.isolator.engine.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.Blocker;
import com.isolator.engine.gfx.ImageUtils;
import com.isolator.game.objects.BaseEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameMap extends GameScene {

    private GridCell[][] gridCells;
    private Size cellSize;
    private Size mapSize;

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
                        Tiles.WOOD_FLOOR,
                        position,
                        cellSize);
            }
        }
    }

    public void addWallsToPerimeter(GameState state) {
        for(int x = 0; x < gridCells.length; x++) {
            for(int y = 0; y < gridCells[0].length; y++) {
                if(x == 0 || y == 0 || x == gridCells.length - 1 || y == gridCells[0].length - 1) {
                    state.addObject(new Blocker(new Position(x * cellSize.getWidth(), y * cellSize.getHeight()), cellSize));
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

    public void setAtRandomAvailableLocation(GameState state, BaseEntity entity) {
        Position randomPosition = new Position(
                (int) (Math.random() * gridCells.length * cellSize.getWidth()),
                (int) (Math.random() * gridCells[0].length * cellSize.getHeight())
        );
        entity.setPosition(randomPosition);

        boolean badPlace = state.getCollidables().stream()
                .filter(collidable -> !(collidable instanceof BaseEntity))
                .anyMatch(gridCell -> gridCell.getCollisionBox().checkCollision(entity.getNextPositionCollisionBox()));

        if(badPlace) {
            setAtRandomAvailableLocation(state, entity);
        }
    }

    public Position getRandomAvailableLocation(GameState state, Size collisionBoxSize) {
        Position randomPosition = new Position(
                (int) (Math.random() * gridCells.length * cellSize.getWidth()),
                (int) (Math.random() * gridCells[0].length * cellSize.getHeight())
        );

        CollisionBox targetCollisionBox = CollisionBox.of(randomPosition, collisionBoxSize);

        boolean badPlace = state.getCollidables().stream()
                .anyMatch(gridCell -> gridCell.getCollisionBox().checkCollision(targetCollisionBox));

        if(badPlace) {
            return getRandomAvailableLocation(state, collisionBoxSize);
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
