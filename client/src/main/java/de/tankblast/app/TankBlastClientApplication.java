package de.tankblast.app;

import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuElement;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.render.PlayerCenteredCamera;
import de.tankblast.render.Renderer;
import de.tankblast.render.Voxel;
import de.tankblast.render.VoxelRenderer;
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
        renderer = new VoxelRenderer();
        renderer.setCamera(new PlayerCenteredCamera());

        currentView = new HomeScreen();

        int i = 0;
        while (true){
            try {
                i++;
                System.out.println("Frame " + i);
                renderer.clear(Colour.GREEN);
                collectVoxels();
                window.showImage(renderer.render(width, height));
                Thread.sleep(100);
            } catch (Exception e){}

        }
    }


    private List<Voxel> collectVoxels(){
        List<Voxel> result = new ArrayList<>();
        for(MenuElement e : currentView.getElements()){
            result.addAll(e.getVoxel());
        }
        return result;
    }


}
