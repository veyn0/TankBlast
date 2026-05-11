package de.tankblast.model.geometry.boundingbox;

import de.tankblast.model.geometry.Vector;

public interface BoundingBox {

    boolean isIntersecting(Vector v);
    boolean isIntersecting(Vector from, Vector direction);

}
