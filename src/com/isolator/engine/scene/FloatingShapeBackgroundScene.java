package com.isolator.engine.scene;

import com.isolator.engine.core.Size;
import com.isolator.engine.game.Scene;
import com.isolator.engine.gfx.ImageUtils;
import com.isolator.engine.state.State;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;


public class FloatingShapeBackgroundScene extends Scene {

    private Size size;
    private List<Shape> shapes;
    private int[] shades;

    public FloatingShapeBackgroundScene(Size windowSize) {
        this.size = windowSize;
        shapes = new ArrayList<>();
        initShapeList();
    }

    private void initShapeList() {
        shades = new int[1500];
        for(int i = 0; i < 1500; i++) {
            Rectangle rectangle = new Rectangle((int)(Math.random() * size.getWidth()), (int)(Math.random() * size.getHeight()), 10, 10);
            shapes.add(rectangle);
            shades[i] = (int)(Math.random() * (255 - 200)) + 200;
        }
    }

    @Override
    public Size getSceneSize() {
        return size;
    }

    @Override
    public void update(State state) {
        for(Shape shape: shapes) {
            Rectangle rectangle = (Rectangle) shape;
            rectangle.translate(1, (int) (Math.random() * 2) + 1);

            if(rectangle.getX() > size.getWidth()) {
                rectangle.x = 0;
            }

            if(rectangle.getY() > size.getHeight()) {
                rectangle.y = 0;
            }
        }
    }

    @Override
    public Image getSceneGraphics(State state) {
        Image sceneImage = ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = (Graphics2D) sceneImage.getGraphics();

        graphics.setColor(new Color(111, 130, 147));
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        for(int i = 0; i < shapes.size(); i++) {
            graphics.setColor(new Color(shades[i], shades[i], shades[i]));
            graphics.fill(shapes.get(i));
        }

        graphics.dispose();
        return sceneImage;
    }
}
