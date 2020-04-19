package com.isolator.engine.gfx;

import com.isolator.engine.GameState;
import com.isolator.engine.core.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.isolator.engine.gfx.ImageUtils.SCALE;
import static com.isolator.engine.gfx.ImageUtils.SPRITE_SIZE;

public class AnimationController {
    private final AnimationSet animationSet;
    private String currentAnimationName;
    private Image currentAnimationSheet;
    private Image currentAnimationFrame;
    private Direction currentDirection;
    private float currentFrameTime;
    private final float frameDisplayTime;
    private int animationFrameIndex;

    public AnimationController(AnimationSet animationSet) {
        this.animationSet = animationSet;
        frameDisplayTime = 20;
        currentFrameTime = 0;
        animationFrameIndex = 0;
        currentAnimationName = "stand";
        currentAnimationSheet = animationSet.get(currentAnimationName);
        currentDirection = Direction.N;
        saveNewCurrentAnimationFrame();
    }

    public void setAnimation(String animationName) {
        if(animationName.equals(currentAnimationName)) {
            return;
        }

        currentAnimationSheet = animationSet.get(animationName);
        currentAnimationName = animationName;
        animationFrameIndex = 0;
    }

    public Image getDrawGraphics() {
        return currentAnimationFrame;
    }

    private void saveNewCurrentAnimationFrame() {
        this.currentAnimationFrame = getCurrentAnimationFrame();
    }

    private Image getCurrentAnimationFrame() {
        return ((BufferedImage)currentAnimationSheet).getSubimage(
                (int) (animationFrameIndex * (SPRITE_SIZE * SCALE)),
                (int) (currentDirection.getAnimationRow() * SPRITE_SIZE * SCALE),
                (int) (SPRITE_SIZE * SCALE),
                (int) (SPRITE_SIZE * SCALE)
        );
    }

    public void update(GameState state, Direction direction) {
        currentFrameTime += 1 * state.getGameSpeed();
        if(currentFrameTime >= frameDisplayTime) {
            nextAnimationFrame();
        }

        if(currentDirection != direction) {
            this.currentDirection = direction;
            saveNewCurrentAnimationFrame();
        }
    }

    private void nextAnimationFrame() {
        currentFrameTime = 0;

        int currentAnimationLength = currentAnimationSheet.getWidth(null) / (int) (SPRITE_SIZE * SCALE);
        if(animationFrameIndex >= currentAnimationLength - 1) {
            animationFrameIndex = 0;
        } else {
            animationFrameIndex++;
        }
        saveNewCurrentAnimationFrame();
    }

    public static AnimationController randomUnit() {
        return new AnimationController(SpritesLibrary.getRandomUnitSet());
    }
}
