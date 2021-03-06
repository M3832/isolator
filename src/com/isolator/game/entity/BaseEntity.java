package com.isolator.game.entity;

import com.isolator.engine.ui.*;
import com.isolator.engine.ui.containers.UIContainer;
import com.isolator.engine.ui.components.UIText;
import com.isolator.engine.ui.containers.VerticalContainer;
import com.isolator.game.controller.Controller;
import com.isolator.engine.core.*;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.gfx.AnimationController;
import com.isolator.engine.gfx.ImageUtils;
import com.isolator.game.states.IsolatorGameState;
import com.isolator.game.gfx.ImageEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntity extends BaseObject {
    private static int ID_COUNTER = 1;

    private final int id;

    protected final Controller controller;
    protected Direction direction;
    protected Vector2 directionVector;
    protected MovementMotor movementMotor;

    protected Size collisionBoxSize;

    protected final Position renderOffset;
    protected final AnimationController animationController;
    protected UIContainer uiContainer;

    protected List<ImageEffect> imageEffects;

    public BaseEntity(Controller controller) {
        super();
        this.id = ID_COUNTER++;
        animationController = AnimationController.randomUnit();
        this.size = new Size(64, 64);
        this.renderOffset = new Position(-32, -48);
        this.collisionBoxSize = new Size(24, 32);
        this.movementMotor = new MovementMotor(0.5f, 3.0f);
        this.controller = controller;
        this.direction = Direction.N;
        this.directionVector = new Vector2();
        imageEffects = new ArrayList<>();
        initUIElements();
    }

    private void initUIElements() {
        this.uiContainer = new VerticalContainer(new Size());
        uiContainer.setBackgroundColor(new Color(0, 0, 0, 0));
        uiContainer.setPadding(new Spacing(0));
        updateDebugContainer();
    }

    private void updateDebugContainer() {
        uiContainer.clear();
        uiContainer.addElement(getDebugUIText());
    }

    @Override
    public Image getDrawGraphics(Camera camera) {
        if(!getImageEffects().isEmpty()) {
            return composeSprite();
        }

        return animationController.getDrawGraphics();
    }

    private Image composeSprite() {
        Image sprite = animationController.getDrawGraphics();
        Image composite = ImageUtils.createCompatibleImage(sprite);
        Graphics2D graphics = (Graphics2D) composite.getGraphics();

        renderEffects(sprite, graphics, ImageEffect.RenderOrder.BEFORE);
        graphics.drawImage(sprite, 0, 0, null);
        renderEffects(sprite, graphics, ImageEffect.RenderOrder.AFTER);

        graphics.dispose();
        return composite;
    }

    private void renderEffects(Image sprite, Graphics2D graphics, ImageEffect.RenderOrder order) {
        for (ImageEffect effect : getImageEffects()) {
            if (effect.getRenderOrder().equals(order)) {
                graphics.drawImage(effect.getEffect(sprite), 0, 0, null);
            }
        }
    }

    protected List<ImageEffect> getImageEffects() {
        return imageEffects;
    }

    public Image getDebugUI() {
        return uiContainer.getUIElement();
    }

    public void push(Vector2 direction, double force) {
        this.movementMotor.apply(direction, force);
    }

    public void update(IsolatorGameState state) {
        movementMotor.update(controller);

        state.getCollisionResolver().checkCollisionsFor(state, this);

        setAnimation();
        animationController.update(state, direction);

        position.apply(movementMotor);
        direction = Direction.fromVelocity(movementMotor, controller, direction);
        if(controller.isRequestingMove())
            directionVector = new Vector2(movementMotor.getDirection());
        updateDebugContainer();
    }

    public void handleCollision(IsolatorGameState state, BaseObject object) {
        if(object instanceof Blocker) {
            CollisionBox box = object.getCollisionBox();
            CollisionBox nextPos = getNextPositionCollisionBox();
            CollisionBox currentBox = getCollisionBox(this.position);

            Position nextPosX = new Position(nextPos.getBox().getX(), currentBox.getBox().getY());
            Position nextPosY = new Position(currentBox.getBox().getX(), nextPos.getBox().getY());
            boolean collideX = CollisionBox.of(nextPosX, collisionBoxSize).checkCollision(box);
            boolean collideY = CollisionBox.of(nextPosY, collisionBoxSize).checkCollision(box);

            immediateStopInDirections(
                    collideX,
                    collideY);
        }
    }

    private void setAnimation() {
         if(movementMotor.isMoving()) {
            animationController.playAnimation("walk");
        } else {
            decideOnAnimation();
        }
    }

    protected void decideOnAnimation() {
        animationController.playAnimation("stand");
    }

    public CollisionBox getNextPositionCollisionBox() {
        Position nextPosition = position.getNextPosition(movementMotor);
        return getCollisionBox(nextPosition);
    }

    private CollisionBox getCollisionBox(Position position) {
        Rectangle collisionBounds = new Rectangle(
                position.getX() - collisionBoxSize.getWidth() / 2,
                position.getY() - collisionBoxSize.getHeight() / 2,
                collisionBoxSize.getWidth(),
                collisionBoxSize.getHeight());

        return new CollisionBox(collisionBounds);
    }

    public UIText getDebugUIText() {
        return new UIText(this.toString(), 12);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + id;
    }

    public void immediateStopInDirections(boolean collideX, boolean collideY) {
        movementMotor.immediateStopInDirections(collideX, collideY);
    }

    public double getCurrentSpeed() {
        return movementMotor.getVelocity();
    }

    public boolean isFacing(Position position) {
        Vector2 direction = Vector2.directionBetweenPositions(position, this.position);
        double dotProduct = Vector2.dotProduct(direction, directionVector);

        return dotProduct > 0;
    }

    public boolean isFacingExactly(Position position) {
        Vector2 direction = Vector2.directionBetweenPositions(position, this.position);
        double dotProduct = Vector2.dotProduct(direction, directionVector);

        return dotProduct > 0.75;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return getNextPositionCollisionBox();
    }

    @Override
    public Position getRenderOffset(Camera camera) {
        Position zoomedOffset = new Position(
                (int)((size.getWidth() * camera.getZoom()) / 2),
                (int)((size.getHeight() * camera.getZoom()) / 2));
        zoomedOffset.subtract(renderOffset);

        return zoomedOffset;
    }

    public void lookAt(Position targetPosition) {
        Vector2 lookDirection = Vector2.directionBetweenPositions(targetPosition, this.position);

        this.direction = Direction.fromVector(lookDirection);
    }

    public boolean isWithinRangeOf(int range, Position position) {
        return position.isWithinRangeOf(range, position);
    }
}
