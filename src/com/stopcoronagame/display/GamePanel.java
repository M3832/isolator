package com.stopcoronagame.display;

import com.stopcoronagame.Game;
import com.stopcoronagame.Handler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public void render(Graphics g, int width, int height) {
        g.clearRect(0, 0, width, height);
        g.fillRect(0, 0, width, height);
        g.dispose();
    }
}
