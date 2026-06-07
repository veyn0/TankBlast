package de.tankblast.model.entity;

import de.tankblast.model.geometry.Location;
import de.tankblast.model.geometry.boundingbox.BoundingBox;

public interface Entity {

    Location getLocation();

    BoundingBox getBoundingBox();

    double getRotationSpeed();

    /** Current facing angle in degrees (0 = +X axis, counter-clockwise positive). */
    double getRotation();

    /** Collision radius in world units (used for the circular collision/bounce model). */
    double getRadius();

    void setLocation(Location location);

    void setRotationSpeed(double rotationSpeed);

    void setRotation(double rotation);

}