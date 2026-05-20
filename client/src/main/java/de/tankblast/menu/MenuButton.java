package de.tankblast.menu;

import de.tankblast.render.Voxel;
import de.tankblast.texture.Texture;

import java.util.List;

public class MenuButton extends MenuElement {

    private final MenuElementLocation location;
    private final Texture texture;

    public MenuButton(MenuElementLocation location, Texture texture) {
        this.location = location;
        this.texture = texture;
    }

    @Override
    public MenuElementLocation getMenuLocation() {
        return location;
    }

    @Override
    public List<Voxel> getVoxel() {
        return List.of();
    }
}
