package de.tankblast.menu.element;

import de.tankblast.render.Voxel;
import de.tankblast.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class MenuButton extends MenuElement {

    private final MenuElementLocation location;
    private Texture texture;

    public MenuButton(MenuElementLocation location, Texture texture) {
        this.location = location;
        this.texture = texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public MenuElementLocation getMenuLocation() {
        return location;
    }

    @Override
    public List<Voxel> getVoxel() {
        int texWidth  = texture.getWidth();
        int texHeight = texture.getHeight();
        int[] data    = texture.getData();

        double pixelSize = location.getWidth() / texWidth;

        List<Voxel> result = new ArrayList<>(texWidth * texHeight);
        for (int py = 0; py < texHeight; py++) {
            for (int px = 0; px < texWidth; px++) {
                int colour = data[py * texWidth + px];

                double worldX = location.getX() + (px + 0.5) * pixelSize;
                double worldY = location.getY() + location.getHeight() - (py + 0.5) * pixelSize;

                result.add(new Voxel(colour, worldX, worldY, pixelSize, 0));
            }
        }
        return result;
    }
}
