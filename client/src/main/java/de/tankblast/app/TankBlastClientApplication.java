package de.tankblast.app;

import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuController;
import de.tankblast.menu.MenuElement;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.render.*;
import de.tankblast.texture.Colour;
import de.tankblast.view.TankBlastWindow;

import java.util.ArrayList;
import java.util.List;

public class TankBlastClientApplication {

    private TankBlastWindow window;
    private Renderer renderer;

    private int width = 1920;
    private int height = 1080;

    private Menu currentView;

    public TankBlastClientApplication(){
        this.window = new TankBlastWindow(width, height);

        Camera camera = new PlayerCenteredCamera();
        this.renderer = new VoxelRenderer();
        renderer.setCamera(camera);

        this.currentView = new HomeScreen();

        MenuController menuController = new MenuController(camera, width, height);
        menuController.setCurrentMenu(currentView);
        window.addMouseInputListener(menuController);

        int i = 0;
        while (true){
            try {
                i++;
                //System.out.println("Frame " + i);
                renderer.clear(Colour.GREEN);
                collectVoxels();
                window.showImage(renderer.render(width, height));
//                Thread.sleep(10);
            } catch (Exception e){}

        }
    }


    private void collectVoxels(){
        for(MenuElement e : currentView.getElements()){
            for(Voxel v : e.getVoxel()){
                renderer.add(v);
            }
        }
    }


}
