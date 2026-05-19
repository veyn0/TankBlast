package de.tankblast.render;

import de.tankblast.model.geometry.Vector;

public class PlayerCenteredCamera implements Camera{

    private Vector position = new Vector(0,0, 50);

    public void setX(double x){
        position = new Vector(x, position.getY(), position.getZ());
    }

    public void setY(double y){
        position = new Vector(position.getX(), y, position.getZ());
    }

    /*
    The Distance is the Distance that is displayed below and above the camera position.
    if the distance is 50, there will be 100 units visible vertically in total
     */
    public void setDistance(double distance){
        position = new Vector(position.getX(), position.getY(), distance);
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public boolean isInFrame(Vector vector, float aspectRatio) {
        return true;
    }
}
