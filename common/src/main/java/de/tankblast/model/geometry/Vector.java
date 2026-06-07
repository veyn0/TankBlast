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

    public Vector add(Vector other){
        return new Vector(x + other.x, y + other.y, z + other.z);
    }

    public Vector subtract(Vector other){
        return new Vector(x - other.x, y - other.y, z - other.z);
    }

    public Vector scale(double factor){
        return new Vector(x * factor, y * factor, z * factor);
    }

    public double dot(Vector other){
        return x * other.x + y * other.y + z * other.z;
    }

    public double length(){
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector normalized(){
        double len = length();
        if (len == 0) return new Vector(0, 0, 0);
        return new Vector(x / len, y / len, z / len);
    }

    public Vector reflect(Vector normal){
        Vector n = normal.normalized();
        return subtract(n.scale(2 * dot(n)));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}