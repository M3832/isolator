package com.isolator.entity;

import com.isolator.controller.Controller;
import com.isolator.core.*;
import com.isolator.game.Game;
import com.isolator.game.GameState;
import com.isolator.game.RunMode;
import com.isolator.ui.UIContainer;
import com.isolator.ui.UIText;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BaseEntity {
    private static int ID_COUNTER = 1;

    private int id;
    private Position position;
    private Direction direction;
    protected Velocity velocity;
    protected Controller controller;
    protected Size size;
    protected UIContainer uiContainer;

    public BaseEntity(Controller controller) {
        this.id = ID_COUNTER++;
        this.position = new Position(0, 0);
        this.size = new Size(50, 50);
        this.velocity = new Velocity(0.5f, 5.0f);
        this.controller = controller;
        this.direction = Direction.N;
        this.uiContainer = new UIContainer();
        initUIElements();
    }

    private void initUIElements() {
        uiContainer.addElement(getDebugUIText());
    }

    public Image getDrawGraphics() {
        BufferedImage bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = bufferedImage.createGraphics();

        drawSprite(imageGraphics);

        return Game.RUN_MODE == RunMode.DEBUG
                ? addUI(bufferedImage)
                : bufferedImage;
    }

    protected void drawSprite(Graphics2D imageGraphics) {
        imageGraphics.setColor(Color.RED);
        imageGraphics.fillRect(0, 0, size.getWidth(), size.getHeight());
    }

    protected BufferedImage addUI(Image image) {
        Image uiContainerImage = uiContainer.getUIElement();
        BufferedImage bufferedImage = new BufferedImage(
                Math.max(size.getWidth(), uiContainerImage.getWidth(null)),
                size.getHeight() + uiContainerImage.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D imageGraphics = (Graphics2D) bufferedImage.getGraphics();

        imageGraphics.drawImage(image, 0, 0, null);
        imageGraphics.drawImage(uiContainerImage, 0, size.getHeight(), null);

        return bufferedImage;
    }

    public void update(GameState state) {
        velocity.update(controller);

        if(state.checkCollisionWithWalls(getNextPositionCollisionBox())) {
            CollisionBox intersection = state.getCollisionIntersection(getNextPositionCollisionBox());
            boolean collideX = intersection.getBox().getWidth() != 0;
            boolean collideY = intersection.getBox().getHeight() != 0;
            velocity.immediateStopInDirections(collideX, collideY);
        }
        position.apply(velocity);
        direction = Direction.fromVelocity(velocity, direction);
    }

    public CollisionBox getNextPositionCollisionBox() {
        Position nextPosition = position.getNextPosition(velocity);
        Rectangle collisionBounds = new Rectangle(
                nextPosition.getX(),
                nextPosition.getY(),
                size.getWidth(),
                size.getHeight());

        return new CollisionBox(collisionBounds);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    };

    public UIText getDebugUIText() {
        return new UIText(this.toString(), 12);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + id;
    }
}
