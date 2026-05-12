package de.tankblast.model.geometry.boundingbox;

import de.tankblast.model.geometry.Vector;

import java.util.List;

public class TriangleBoundingBox implements BoundingBox{

    @Override
    public boolean isIntersecting(Vector v) {
        return false;
    }

    @Override
    public boolean isIntersecting(Vector from, Vector direction) {
        return false;
    }

    @Override
    public List<Vector> getVectors() {
        return List.of();
    }
}
