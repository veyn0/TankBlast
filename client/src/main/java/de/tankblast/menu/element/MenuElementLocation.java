package de.tankblast.menu.element;

public class MenuElementLocation {
    private double x, y, width, height;

    public MenuElementLocation(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isBetween(double x, double y){
        double minX = Math.min(this.x, this.x + width);
        double maxX = Math.max(this.x, this.x + width);
        double minY = Math.min(this.y, this.y + height);
        double maxY = Math.max(this.y, this.y + height);
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
