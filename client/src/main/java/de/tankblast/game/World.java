package de.tankblast.game;

import de.tankblast.model.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class World {

    private List<Entity> entities = new ArrayList<>();

    public List<Entity> getEntities() {
        return entities;
    }
}
