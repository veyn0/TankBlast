package de.tankblast.model.entity;

import de.tankblast.model.geometry.Location;
import de.tankblast.model.geometry.Vector;
import de.tankblast.model.geometry.Velocity;
import de.tankblast.model.geometry.boundingbox.BoundingBox;
import de.tankblast.model.geometry.boundingbox.CircularBoundingBox;

public class Obstacle implements Entity {

    private Location location;
    private final double radius;

    public Obstacle(Vector position, double radius) {
        this.location = new Location(position, new Velocity(new Vector(0, 1, 0), 0));
        this.radius = radius;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new CircularBoundingBox();
    }

    @Override
    public double getRotationSpeed() {
        return 0;
    }

    @Override
    public double getRotation() {
        return 0;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void setRotationSpeed(double rotationSpeed) {
        // static
    }

    @Override
    public void setRotation(double rotation) {
        // static
    }
}