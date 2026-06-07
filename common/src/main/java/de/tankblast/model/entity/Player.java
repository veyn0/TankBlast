package de.tankblast.model.entity;

import de.tankblast.model.geometry.Location;
import de.tankblast.model.geometry.Vector;
import de.tankblast.model.geometry.Velocity;
import de.tankblast.model.geometry.boundingbox.BoundingBox;
import de.tankblast.model.geometry.boundingbox.CircularBoundingBox;

public class Player implements Entity {

    private static final double DEFAULT_RADIUS = 2.0;

    private Location location;
    private double rotation;       // facing angle in degrees
    private double rotationSpeed;  // degrees per second (max)
    private final double radius;

    public Player() {
        this(new Vector(0, 0, 0), 90.0, DEFAULT_RADIUS);
    }

    public Player(Vector position, double rotation, double radius) {
        this.location = new Location(position, new Velocity(new Vector(0, 1, 0), 0));
        this.rotation = rotation;
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
        return rotationSpeed;
    }

    @Override
    public double getRotation() {
        return rotation;
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
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}