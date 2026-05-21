package de.tankblast.menu.menus.homescreen;

import de.tankblast.menu.*;
import de.tankblast.render.GraphicsComponent;
import de.tankblast.render.Voxel;
import de.tankblast.texture.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeScreen implements Menu, ElementInteractionListener, GraphicsComponent {

    private List<MenuElement> elements = new ArrayList<>();

    public HomeScreen(){
        Texture texture = createPlaceHolderTexture(4, 1);
        MenuElementLocation location = new MenuElementLocation(10,0, 40,10);
        elements.add(new MenuButton(location, texture));
    }

    @Override
    public void onElementInteract(ElementInteractionEvent event) {
        if(!elements.contains(event.getElement())) return;
    }

    @Override
    public List<MenuElement> getElements() {
        return elements;
    }

    @Override
    public List<ElementInteractionListener> getInteractionListener() {
        return List.of(this);
    }

    private Texture createPlaceHolderTexture(int width, int height) {
        int[] data = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = (int) (255.0 * x / Math.max(1, width - 1));
                int g = (int) (255.0 * y / Math.max(1, height - 1));
                int b = 128;
                data[y * width + x] = 0xFF000000 | (r << 16) | (g << 8) | b;
            }
        }
        return new Texture(data, width);
    }

    @Override
    public List<Voxel> getVoxel() {
        List<Voxel> result = new ArrayList<>();
        for(MenuElement m : elements){
            result.addAll(m.getVoxel());
        }
        return result;
    }
}
