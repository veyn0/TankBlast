package de.tankblast.game;

import de.tankblast.model.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    private final List<Entity> entities = new CopyOnWriteArrayList<>();

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> snapshot() {
        return new ArrayList<>(entities);
    }
}