package de.tankblast.texture.text;

import java.awt.*;

public class TextInfo {

    private String text;
    private int x, y, size;
    private Color color = Color.WHITE;

    public TextInfo(String text, int x, int y, int size) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public TextInfo(String text, int x, int y, int size, Color color) {
        this(text, x, y, size);
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }
}
