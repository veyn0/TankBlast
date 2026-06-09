package de.tankblast.render;

import de.tankblast.model.entity.Entity;
import de.tankblast.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class EntityGraphicsComponent implements GraphicsComponent {

    private final Entity entity;
    private final Texture texture;

    public EntityGraphicsComponent(Entity entity, Texture texture) {
        this.entity = entity;
        this.texture = texture;
    }

    @Override
    public List<Voxel> getVoxel() {
        int texWidth = texture.getWidth();
        int texHeight = texture.getHeight();
        int[] data = texture.getData();

        double diameter = entity.getRadius() * 2.0;
        double pixelSize = diameter / texWidth;

        double centerX = entity.getLocation().getPosition().getX();
        double centerY = entity.getLocation().getPosition().getY();

        double rotationRad = Math.toRadians(entity.getRotation());
        double cos = Math.cos(rotationRad);
        double sin = Math.sin(rotationRad);

        List<Voxel> result = new ArrayList<>(texWidth * texHeight);
        for (int py = 0; py < texHeight; py++) {
            for (int px = 0; px < texWidth; px++) {
                int colour = data[py * texWidth + px];
                if ((colour >>> 24) == 0) continue;
                double localX = (px + 0.5 - texWidth / 2.0) * pixelSize;
                double localY = (texHeight / 2.0 - (py + 0.5)) * pixelSize;

                double rotatedX = localX * cos - localY * sin;
                double rotatedY = localX * sin + localY * cos;

                double worldX = centerX + rotatedX;
                double worldY = centerY + rotatedY;

                result.add(new Voxel(colour, worldX, worldY, pixelSize, -rotationRad));
            }
        }
        return result;
    }
}