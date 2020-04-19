package com.isolator.display;

import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.game.GameState;
import com.isolator.game.RunMode;
import com.isolator.gfx.ImageUtils;
import com.isolator.map.GridCell;
import com.isolator.ui.UIAlignmentUtils;

import java.awt.*;
import java.util.List;

public class Renderer {

    DebugRenderer debugRenderer;

    public Renderer() {
        this.debugRenderer = new DebugRenderer();
    }

    public void renderState(GameState state, Size windowSize, Graphics2D screenGraphics) {
        renderMap(state, screenGraphics);
        renderEntities(state, screenGraphics);
        renderUI(state, windowSize, screenGraphics);

        if(state.getRunMode() == RunMode.DEBUG) {
            debugRenderer.render(List.of(), state, screenGraphics);
        }
    }

    private void renderEntities(GameState state, Graphics2D screenGraphics) {
        Camera camera = state.getCamera();
        double zoom = camera.getZoom();

        state.getViewableEntities().forEach(entity -> {
            Position entPos = entity.getPosition();
            Position renderOffset = entity.getRenderOffset();
            Position camPos = camera.getPosition();
            Size entSize = entity.getSize();

            screenGraphics.drawImage(
                    ImageUtils.scale(entity.getDrawGraphics(), camera.getZoom()),
                    entPos.getX() - camPos.getX() + renderOffset.getX() - (int)(entSize.getWidth() * zoom) / 2,
                    entPos.getY() - camPos.getY() + renderOffset.getY() - (int)(entSize.getHeight() * zoom) / 2,
                    null);
        });
    }

    private void renderMap(GameState state, Graphics2D screenGraphics) {
        Camera camera = state.getCamera();
        GridCell[][] tiles = state.getMap().getGridCells();
        Size cellSize = state.getMap().getCellSize();
        Position startRenderingPosition = state.getMap().getViewableStartingPosition(camera);
        Position endRenderingPosition = state.getMap().getViewableEndingPosition(camera);

        for(int x = startRenderingPosition.getX(); x < Math.min(tiles.length, endRenderingPosition.getX()); x++) {
            for(int y = startRenderingPosition.getY(); y < Math.min(tiles[0].length, endRenderingPosition.getY()); y++) {
                screenGraphics.drawImage(
                        tiles[x][y].getTileSprite(),
                        x * cellSize.getWidth() - camera.getPosition().getX(),
                        y * cellSize.getHeight() - camera.getPosition().getY(),
                        null
                );
            }
        }
    }

    private void renderUI(GameState state, Size windowSize, Graphics2D screenGraphics) {
        state.getUiContainers().forEach(container -> {
            Position drawPosition = UIAlignmentUtils.calculateDrawPosition(container, screenGraphics, windowSize);
            screenGraphics.drawImage(
                    container.getUIElement(),
                    drawPosition.getX(),
                    drawPosition.getY(),
                    null
            );
        });
    }
}
