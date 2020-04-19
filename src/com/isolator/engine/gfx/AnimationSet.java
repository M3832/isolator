package com.isolator.engine.gfx;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AnimationSet {

    private final Map<String, Image> animationSheets;

    public AnimationSet() {
        this.animationSheets = new HashMap<>();
    }

    public void addAnimationSheet(String name, Image animationSheet) {
        animationSheets.put(name, animationSheet);
    }

    public Image get(String animationName) {
        return animationSheets.get(animationName);
    }
}
