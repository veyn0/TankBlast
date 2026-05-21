package de.tankblast.menu.menus.homescreen;

import de.tankblast.menu.*;
import de.tankblast.menu.event.ElementClickEvent;
import de.tankblast.menu.event.ElementStartHoverEvent;
import de.tankblast.menu.event.ElementStopHoverEvent;
import de.tankblast.render.GraphicsComponent;
import de.tankblast.render.Voxel;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen implements Menu, ElementInteractionListener, GraphicsComponent {

    private List<MenuElement> elements = new ArrayList<>();

    private final Object lock = new Object();

    private Texture button1;
    private Texture button2;

    public HomeScreen(){
        synchronized (lock) {
            button1 = new ImageTextureLoader().loadResource("textures/buttons/button_1_0.png");
            button2 = new ImageTextureLoader().loadResource("textures/buttons/button_1_1.png");

            Texture texture = button1;
            MenuElementLocation location = new MenuElementLocation(10, 0, 40, 10);
            elements.add(new MenuButton(location, texture));

        }
    }


    @Override
    public void onElementInteract(ElementInteractionEvent event) {
        synchronized (lock) {
            //if(!elements.contains(event.getElement())) return;
            if (event instanceof ElementStopHoverEvent e) {
                System.out.println("hover stopped");
                if(elements.getFirst() instanceof MenuButton b){
                    b.setTexture(button1);
                }
            } else if (event instanceof ElementStartHoverEvent e) {
                System.out.println("hover started");
                if(elements.getFirst() instanceof MenuButton b){
                    b.setTexture(button2);
                }
            }
        }
    }

    @Override
    public List<MenuElement> getElements() {
        synchronized (lock) {
            return elements;
        }
    }

    @Override
    public List<ElementInteractionListener> getInteractionListener() {
        synchronized (lock) {
            return List.of(this);
        }
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
        synchronized (lock) {
            List<Voxel> result = new ArrayList<>();
            for (MenuElement m : elements) {
                result.addAll(m.getVoxel());
            }
            return result;
        }
    }
}
