package de.tankblast.render;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class VoxelRenderer implements Renderer{

    private final Object lock = new Object();

    private List<Voxel> voxels = new ArrayList<>();
    private Camera camera;


    @Override
    public void clear(int colour) {
        synchronized (lock){
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
        return null;
    }
}
