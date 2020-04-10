package com.stopcoronagame.display;

import com.stopcoronagame.Handler;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    private Handler handler;
    private GamePanel panel;
    private int width, height;

    public Display(int width, int height) {
        handler = Handler.getHandler();
        this.width = width;
        this.height = height;

        handler.setDisplay(this);

        setupWindow();
    }

    private void setupWindow() {
        setTitle("Halt the Spread");
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new GamePanel();
        panel.setSize(width, height);
        panel.setMinimumSize(new Dimension(width, height));
        panel.setMaximumSize(new Dimension(width, height));

        Container graphicsContainer = getContentPane();
        graphicsContainer.setLayout(new BoxLayout(graphicsContainer, BoxLayout.PAGE_AXIS));
        graphicsContainer.add(panel);

        setVisible(true);
        createBufferStrategy(3);
    }

    public int getGameWidth() {
        return width;
    }

    public int getGameHeight() {
        return height;
    }

    public void render() {
        panel.render(getGraphics(), width, height);
    }
}
