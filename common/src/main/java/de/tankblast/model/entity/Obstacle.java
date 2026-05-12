package de.tankblast.model.entity;

import de.tankblast.model.geometry.Location;
import de.tankblast.model.geometry.boundingbox.BoundingBox;

public class Obstacle implements Entity{

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }

    @Override
    public double getRotationSpeed() {
        return 0;
    }

    @Override
    public void setLocation(Location location) {

    }

    @Override
    public void setRotationSpeed(double rotationSpeed) {

    }
}
