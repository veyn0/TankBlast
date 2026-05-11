package de.tankblast.model.entity;

import de.tankblast.model.geometry.Location;
import de.tankblast.model.geometry.Velocity;
import de.tankblast.model.geometry.boundingbox.BoundingBox;

public interface Entity {

    Location getLocation();

    BoundingBox getBoundingBox();

    double getRotationSpeed();

    void setLocation(Location location);

    void setRotationSpeed(double rotationSpeed);

}
