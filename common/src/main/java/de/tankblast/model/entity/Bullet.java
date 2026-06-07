package de.tankblast.model.entity;

import de.tankblast.model.geometry.Location;
import de.tankblast.model.geometry.Vector;
import de.tankblast.model.geometry.Velocity;
import de.tankblast.model.geometry.boundingbox.BoundingBox;
import de.tankblast.model.geometry.boundingbox.CircularBoundingBox;

import java.util.UUID;

public class Bullet implements Entity {

    private static final double DEFAULT_RADIUS = 0.4;

    private final UUID ownerId;
    private Location location;
    private double rotation;
    private final double radius;

    private int bouncesRemaining;
    private boolean dead;

    public Bullet(UUID ownerId, Vector position, Vector direction, double speed, int maxBounces) {
        this.ownerId = ownerId;
        this.location = new Location(position, new Velocity(direction.normalized(), speed));
        this.rotation = Math.toDegrees(Math.atan2(direction.getY(), direction.getX()));
        this.radius = DEFAULT_RADIUS;
        this.bouncesRemaining = maxBounces;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public int getBouncesRemaining() {
        return bouncesRemaining;
    }

    public void registerBounce() {
        bouncesRemaining--;
        if (bouncesRemaining < 0) {
            dead = true;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        this.dead = true;
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
        // bullets don't rotate on input
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}