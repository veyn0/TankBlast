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
        Texture texture = createPlaceHolderTexture();
        MenuElementLocation location = new MenuElementLocation(0,0, 20,10);
        elements.add(new MenuButton( location, texture));
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


    private Texture createPlaceHolderTexture(){
        int width = 20;
        int height = 10;
        int[] data = new int[width * height];
        Arrays.fill(data, 0xFF808080);
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
