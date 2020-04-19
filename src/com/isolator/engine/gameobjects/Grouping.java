package com.isolator.engine.gameobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grouping {

    public static final int ENTITIES = 1;
    public static final int PROPS = 2;

    private List<BaseObject> objects;

    public Grouping() {
        objects = new ArrayList<>();
    }

    public void addObject(BaseObject object) {
        objects.add(object);
    }

    public List<BaseObject> getObjects() {
        return Collections.unmodifiableList(objects);
    }
}
