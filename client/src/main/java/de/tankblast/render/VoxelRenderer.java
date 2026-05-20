package de.tankblast.render;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class VoxelRenderer implements Renderer{

    private final Object lock = new Object();

    private List<Voxel> voxels = new ArrayList<>();
    private Camera camera;

    private int backgroundColour;

    @Override
    public void clear(int colour) {
        synchronized (lock){
            this.backgroundColour = colour;
            voxels.clear();
        }
    }

    @Override
    public void add(Voxel voxel) {
        synchronized (lock){
            voxels.add(voxel);
        }
    }

    @Override
    public void add(GraphicsComponent graphicsComponent) {
        synchronized (lock){
            for(Voxel v : graphicsComponent.getVoxel()){
                add(v);
            }
        }
    }

    @Override
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public BufferedImage render(int width, int height) {
        synchronized (lock) {
            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = result.createGraphics();
            try {
                g.setColor(new Color(backgroundColour));
                g.fillRect(0, 0, width, height);

                double pixelPerUnit = height / (2.0 * camera.getPosition().getZ());
                double cameraX = camera.getPosition().getX();
                double cameraY = camera.getPosition().getY();

                for (Voxel v : voxels) {
                    double screenX = width  / 2.0 + (v.getX() - cameraX) * pixelPerUnit;
                    double screenY = height / 2.0 - (v.getY() - cameraY) * pixelPerUnit;
                    double screenSize = v.getSize() * pixelPerUnit;

                    g.setColor(new Color(v.getColour()));

                    if (v.getRotation() != 0.0) {
                        AffineTransform old = g.getTransform();
                        g.rotate(v.getRotation(), screenX, screenY);
                        g.fill(new Rectangle2D.Double(screenX - screenSize /2, screenY - screenSize /2, screenSize, screenSize));
                        g.setTransform(old);
                    } else {
                        g.fill(new Rectangle2D.Double(screenX - screenSize /2, screenY - screenSize /2, screenSize, screenSize));
                    }
                }
            } finally {
                g.dispose();
            }
            return result;
        }
    }

}
