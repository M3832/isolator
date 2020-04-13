package com.isolator.display;

import com.isolator.controller.Input;
import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.game.GameState;
import com.isolator.map.GridCell;
import com.isolator.ui.UIAlignmentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends JFrame {
    private Canvas canvas;
    private Size size;

    public Display(int width, int height, Input input) {
        super();
        size = new Size(width, height);
        addKeyListener(input);
        setupWindow();
    }

    private void setupWindow() {
        java.awt.Dimension windowSize = new java.awt.Dimension(this.size.getWidth(), this.size.getHeight());
        setTitle("Halt the Spread");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(windowSize);
        canvas.setFocusable(false);
        add(canvas);
        pack();
        canvas.createBufferStrategy(3);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render(GameState state) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics2D screenGraphics = (Graphics2D) bufferStrategy.getDrawGraphics();

        clearScreen(screenGraphics);
        renderState(state, screenGraphics);

        screenGraphics.dispose();
        bufferStrategy.show();
    }

    private void clearScreen(Graphics2D screenGraphics) {
        Rectangle graphicsBounds = screenGraphics.getDeviceConfiguration().getBounds();
        screenGraphics.setColor(Color.BLACK);
        screenGraphics.fillRect(0, 0, (int) graphicsBounds.getWidth(), (int) graphicsBounds.getHeight());
    }

    public void renderState(GameState state, Graphics2D screenGraphics) {
        renderMap(state, screenGraphics);
        renderEntities(state, screenGraphics);
        renderUI(state, screenGraphics);
    }

    private void renderEntities(GameState state, Graphics2D screenGraphics) {
        Camera camera = state.getCamera();
        Position startRenderingPosition = state.getMap().getViewableStartingPosition(camera);
        Position endRenderingPosition = state.getMap().getViewableEndingPosition(camera);

        state.getEntities()
                .stream()
                .filter(entity -> {
                    int y = (entity.getPosition().getY() / state.getCellSize().getHeight());
                    int x = (entity.getPosition().getX() / state.getCellSize().getWidth());

                    return y > startRenderingPosition.getY() - 2 && y < endRenderingPosition.getY()
                            && x > startRenderingPosition.getX() - 3 && x < endRenderingPosition.getX();
                })
                .forEach(entity -> screenGraphics.drawImage(
                                        entity.getDrawGraphics(),
                                        entity.getPosition().getX() - camera.getPosition().getX(),
                                        entity.getPosition().getY() - camera.getPosition().getY(),
                                        null));
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

    private void renderUI(GameState state, Graphics2D screenGraphics) {
        state.getUiContainers().forEach(container -> {
            Position drawPosition = UIAlignmentUtils.calculateDrawPosition(container, screenGraphics, size);
            screenGraphics.drawImage(
                    container.getUIElement(),
                    drawPosition.getX(),
                    drawPosition.getY(),
                    null
            );
        });
    }
}
