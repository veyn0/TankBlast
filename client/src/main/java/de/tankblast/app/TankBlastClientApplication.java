package de.tankblast.app;

import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuController;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.render.*;
import de.tankblast.texture.Colour;
import de.tankblast.view.TankBlastWindow;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankBlastClientApplication {

    private TankBlastWindow window;
    private Renderer renderer;

    private int width = 1920;
    private int height = 1080;

    private Menu currentView;

    private MenuController menuController;

    private volatile boolean running = true;

    public TankBlastClientApplication(){
        this.window = new TankBlastWindow(width, height);

        Camera camera = new PlayerCenteredCamera();
        this.renderer = new VoxelRenderer();
        renderer.setCamera(camera);

        this.currentView = new HomeScreen(this);

        menuController = new MenuController(camera, width, height);
        menuController.setCurrentMenu(currentView);
        window.addMouseInputListener(menuController);

        long time = System.currentTimeMillis();
        List<Integer> frametimes = new ArrayList<>();
        while (running){
            try {
                time = System.currentTimeMillis();
                renderer.clear(Colour.GREEN);
                collectVoxels();
                window.showImage(renderer.render(width, height));
                int frametime = Math.toIntExact(System.currentTimeMillis() - time);
                frametimes.add(frametime);

                if(frametimes.size()>=100){
                    double avg = 0;
                    for(int i : frametimes){
                        avg+=frametime;
                    }
                    avg = avg/frametimes.size();
                    if(avg > 5) {
                        System.out.println(avg + "ms avg frametime");
                    }
                    frametimes.clear();
                }

            } catch (Exception e){}

        }
    }

    public void setCurrentView(Menu menu){
        this.currentView = menu;
        menuController.setCurrentMenu(menu);
    }

    private void collectVoxels(){
        for(Voxel v  : currentView.getVoxel()){
            renderer.add(v);
        }
    }

    public void exit(){
        this.running = false;
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
