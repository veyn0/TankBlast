package de.tankblast.texture;

public class Texture {

    private int[] data;
    private int width;

    public Texture(int[] data, int width) {
        this.data = data;
        this.width = width;
    }

    public int[] getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return data.length / width;
    }
}
