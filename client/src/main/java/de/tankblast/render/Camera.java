package de.tankblast.render;

import de.tankblast.model.geometry.Vector;

public interface Camera {

    public Vector getPosition();

    public boolean isInFrame(Vector vector, float aspectRatio);

}
