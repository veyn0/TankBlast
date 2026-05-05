package de.tankblast.model.geometry;

public class Vector {
    private double x, y, z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector copy(){
        return new Vector(x,y,z);
    }

    public Vector copy(double offsetX, double offsetY, double offsetZ){
        return new Vector( x + offsetX, y + offsetY, z + offsetZ);
    }

}
