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

    public FloatingShapeBackgroundScene(Size windowSize) {
        this.size = windowSize;
        shapes = new ArrayList<>();
        initShapeList();
    }

    private void initShapeList() {
        for(int i = 0; i < 250; i++) {
            Rectangle rectangle = new Rectangle((int)(Math.random() * size.getWidth()), (int)(Math.random() * size.getHeight()), 20, 20);
            shapes.add(rectangle);
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
            int color = Math.max(200, Math.min(245, 2 * (i + 255 - shapes.size())));
            graphics.setColor(new Color(color, color, color));
            graphics.fill(shapes.get(i));
        }

        graphics.dispose();
        return sceneImage;
    }
}
