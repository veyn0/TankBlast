package de.tankblast.menu.element;

public class MouseLocation {

    private MenuElement element;
    private double x, y;

    public MouseLocation(MenuElement element, double x, double y) {
        this.element = element;
        this.x = x;
        this.y = y;
    }

    public MenuElement getElement() {
        return element;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
