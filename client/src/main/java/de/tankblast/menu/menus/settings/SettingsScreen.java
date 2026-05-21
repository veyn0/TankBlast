package de.tankblast.menu.menus.settings;

import de.tankblast.menu.ElementInteractionEvent;
import de.tankblast.menu.ElementInteractionListener;
import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuElement;
import de.tankblast.render.GraphicsComponent;
import de.tankblast.render.Voxel;

import java.util.List;

public class SettingsScreen extends Menu implements ElementInteractionListener {
    @Override
    public void onElementInteract(ElementInteractionEvent event) {

    }

    @Override
    public List<MenuElement> getElements() {
        return List.of();
    }

    @Override
    public List<ElementInteractionListener> getInteractionListener() {
        return List.of();
    }

    @Override
    public List<Voxel> getVoxel() {
        return List.of();
    }
}
