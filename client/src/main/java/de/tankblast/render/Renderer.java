package de.tankblast.render;

import java.awt.image.BufferedImage;

public interface Renderer {

    public void clear(int colour);

    public void add(Voxel voxel);

    public void add(GraphicsComponent graphicsComponent);

    public void setCamera(Camera camera);

    public BufferedImage render(int width, int height);

}
