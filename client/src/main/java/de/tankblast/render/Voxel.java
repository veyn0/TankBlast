package de.tankblast.render;

public class Voxel {

    private int colour;
    private double x, y, size, rotation;

    public Voxel(int colour, double x, double y, double size, double rotation) {
        this.colour = colour;
        this.x = x;
        this.y = y;
        this.size = size;
        this.rotation = rotation;
    }

    public int getColour() {
        return colour;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public double getRotation() {
        return rotation;
    }
}
