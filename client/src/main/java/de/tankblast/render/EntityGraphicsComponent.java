package de.tankblast.render;

import de.tankblast.model.entity.Entity;
import de.tankblast.texture.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Renders a single {@link Entity} as a textured square centered on its world
 * position, sized to its collision radius and rotated to its facing angle.
 *
 * <p>Rotation is applied to each voxel's <em>position</em> around the entity
 * center (so the whole texture turns as one rigid shape), and additionally to
 * each voxel's own orientation (so pixel edges line up). Applying only the
 * latter would spin each square in place while leaving the overall image
 * unrotated.
 */
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

        // Screen Y grows downward while world Y grows upward, so a
        // counter-clockwise world rotation is a clockwise screen rotation.
        // We rotate voxel positions in world space here and hand the same angle
        // (negated for the screen convention) to the voxel for its own spin.
        double rotationRad = Math.toRadians(entity.getRotation());
        double cos = Math.cos(rotationRad);
        double sin = Math.sin(rotationRad);

        List<Voxel> result = new ArrayList<>(texWidth * texHeight);
        for (int py = 0; py < texHeight; py++) {
            for (int px = 0; px < texWidth; px++) {
                int colour = data[py * texWidth + px];
                if ((colour >>> 24) == 0) continue; // skip fully transparent

                // local position relative to texture center, in world units
                double localX = (px + 0.5 - texWidth / 2.0) * pixelSize;
                double localY = (texHeight / 2.0 - (py + 0.5)) * pixelSize;

                // rotate the local offset around the center (world space, CCW)
                double rotatedX = localX * cos - localY * sin;
                double rotatedY = localX * sin + localY * cos;

                double worldX = centerX + rotatedX;
                double worldY = centerY + rotatedY;

                // voxel's own spin uses the screen convention (negated angle)
                result.add(new Voxel(colour, worldX, worldY, pixelSize, -rotationRad));
            }
        }
        return result;
    }
}