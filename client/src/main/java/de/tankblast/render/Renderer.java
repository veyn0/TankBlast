package de.tankblast.render;

public interface Renderer {

    public void clear(int colour);

    public void render(Pixel pixel);

    public void render(GraphicsComponent graphicsComponent);

    public void setCamera(Camera camera);

}
