package de.tankblast.menu.menus.homescreen;

import de.tankblast.menu.*;
import de.tankblast.menu.event.ElementClickEvent;
import de.tankblast.menu.event.ElementStartHoverEvent;
import de.tankblast.menu.event.ElementStopHoverEvent;
import de.tankblast.render.GraphicsComponent;
import de.tankblast.render.Voxel;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.Texture;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends Menu implements ElementInteractionListener {

    private List<MenuElement> elements = new ArrayList<>();

    private final Object lock = new Object();

    private InteractableButton playButton;
    private InteractableButton settingsButton;

    private BackGround background;

    public HomeScreen(){
        synchronized (lock) {

            MenuElementLocation backgroundLocation = new MenuElementLocation(-90,-50, 180,100);
            Texture background = new ImageTextureLoader().loadResource("textures/background/background.png");
            this.background = new BackGround(backgroundLocation, background);

            System.out.println(this.background.getVoxel().size());


            Texture play0 = new ImageTextureLoader().loadResource("textures/buttons/button_play_0.png");
            Texture play1 = new ImageTextureLoader().loadResource("textures/buttons/button_play_1.png");
            Texture settings0 = new ImageTextureLoader().loadResource("textures/buttons/button_settings_0.png");
            Texture settings1 = new ImageTextureLoader().loadResource("textures/buttons/button_settings_1.png");

            MenuElementLocation playButtonLocation = new MenuElementLocation(-20, 10, 40, 10);
            playButton = new InteractableButton(playButtonLocation, play0, play1, () ->{
                System.out.println("Clicked the Play Button");
            });
            elements.add(playButton);

            MenuElementLocation settingsButtonLocation = new MenuElementLocation(-20, -10, 40, 10);
            settingsButton = new InteractableButton(settingsButtonLocation, settings0, settings1, () ->{
                System.out.println("Clicked the Settings Button");
            });
            elements.add(settingsButton);


        }
    }


    @Override
    public void onElementInteract(ElementInteractionEvent event) {
        synchronized (lock) {
            playButton.onElementInteractionEvent(event);
            settingsButton.onElementInteractionEvent(event);
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
            List<Voxel> result = new ArrayList<>(background.getVoxel());
            for (MenuElement m : elements) {
                result.addAll(m.getVoxel());
            }
            return result;
        }
    }
}
