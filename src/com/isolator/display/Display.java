package com.isolator.display;

import com.isolator.controller.Input;
import com.isolator.core.Size;
import com.isolator.game.GameState;
import com.isolator.map.BaseTile;
import com.isolator.map.GameMap;

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
    }

    private void renderEntities(GameState state, Graphics2D screenGraphics) {
        state.getEntities().forEach(entity -> {
            screenGraphics.drawImage(
                    entity.getSprite(),
                    entity.getPosition().getX(),
                    entity.getPosition().getY(),
                    null);
        });
    }

    private void renderMap(GameState state, Graphics2D screenGraphics) {
        BaseTile[][] tiles = state.getMap().getTiles();
        Size cellSize = state.getMap().getCellSize();

        for(int x = 0; x < tiles.length; x++) {
            for(int y = 0; y < tiles[0].length; y++) {
                screenGraphics.drawImage(
                        tiles[x][y].getTileSprite(),
                        x * cellSize.getWidth(),
                        y * cellSize.getHeight(),
                        null
                );
            }
        }
    }
}
