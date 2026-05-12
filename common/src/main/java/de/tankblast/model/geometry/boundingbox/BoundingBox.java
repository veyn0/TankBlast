package de.tankblast.model.geometry.boundingbox;

import de.tankblast.model.geometry.Vector;

import java.util.List;

public interface BoundingBox {

    boolean isIntersecting(Vector v);

    boolean isIntersecting(Vector from, Vector direction);

    List<Vector> getVectors();

}
