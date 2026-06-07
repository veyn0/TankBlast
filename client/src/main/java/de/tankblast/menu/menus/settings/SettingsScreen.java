package de.tankblast.menu.menus.settings;

import de.tankblast.app.TankBlastClientApplication;
import de.tankblast.menu.*;
import de.tankblast.menu.element.*;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.render.Voxel;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class SettingsScreen extends Menu implements ElementInteractionListener {

    private List<MenuElement> elements = new ArrayList<>();

    private final Object lock = new Object();

    private InteractableButton backButton;

    private Slider volumeSlider;

    private BackGround background;

    private TankBlastClientApplication tankBlastClientApplication;


    public SettingsScreen(TankBlastClientApplication tankBlastClientApplication, HomeScreen homeScreen){
        synchronized (lock) {
            this.tankBlastClientApplication = tankBlastClientApplication;

            MenuElementLocation backgroundLocation = new MenuElementLocation(-90,-50, 180,100);
            Texture background = new ImageTextureLoader().loadResource("textures/background/settings.png");
            this.background = new BackGround(backgroundLocation, background);

            System.out.println(this.background.getVoxel().size());

            Texture back0 = new ImageTextureLoader().loadResource("textures/buttons/button_back_0.png");
            Texture back1 = new ImageTextureLoader().loadResource("textures/buttons/button_back_1.png");

            MenuElementLocation backButtonLocation = new MenuElementLocation(-20, 10, 40, 10);
            backButton = new InteractableButton(backButtonLocation,back1, back0, ()->{
                tankBlastClientApplication.setCurrentView(homeScreen);
            });
            elements.add(backButton);


            MenuElementLocation volumeLocation = new MenuElementLocation(-40,-10, 80,10);
            Texture volumeSliderTexture = new ImageTextureLoader().loadResource("textures/slider/slider.png");
            Texture volumeSliderHeadTexture = new ImageTextureLoader().loadResource("textures/slider/slider_head.png");
            volumeSlider = new Slider(volumeLocation, volumeSliderTexture, volumeSliderHeadTexture, 100, 50, i ->{
                System.out.println("settings clicked " + i);
            });
            elements.add(volumeSlider);
        }
    }

    @Override
    public void onElementInteract(ElementInteractionEvent event) {
        synchronized (lock) {
            backButton.onElementInteractionEvent(event);
            volumeSlider.onEvent(event);
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
