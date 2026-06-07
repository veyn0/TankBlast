package de.tankblast.app;

import de.tankblast.menu.BackGround;
import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuController;
import de.tankblast.menu.element.InteractableButton;
import de.tankblast.menu.element.MenuElementLocation;
import de.tankblast.menu.menus.GenericMenu;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.network.NetworkManager;
import de.tankblast.network.StatusNetworkController;
import de.tankblast.protocol.dto.AvailableGame;
import de.tankblast.render.*;
import de.tankblast.texture.Colour;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.Texture;
import de.tankblast.texture.text.TextInfo;
import de.tankblast.texture.text.TextTextureCreator;
import de.tankblast.view.TankBlastWindow;

import java.awt.*;
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

    private NetworkManager networkManager;

    public TankBlastClientApplication(){
        this.window = new TankBlastWindow(width, height);

        Camera camera = new PlayerCenteredCamera();
        this.renderer = new VoxelRenderer();
        renderer.setCamera(camera);

        menuController = new MenuController(camera, width, height);
        setCurrentView(new HomeScreen(this));
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
                       // System.out.println(avg + "ms avg frametime");
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

    public GenericMenu createAvailableGamesScreen(){
        ImageTextureLoader loader = new ImageTextureLoader();
        GenericMenu result = new GenericMenu(new BackGround(new MenuElementLocation(-90, -50, 185, 100), loader.loadResource("textures/background/games.png"))); //TODO: add background

        //back button
        result.addButton(new InteractableButton(
                new MenuElementLocation(-45,-45,40,10),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/back.png"),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/back_hover.png"),
                () ->{
                    setCurrentView(new HomeScreen(this));
                })
        );

        //refresh button
        result.addButton(new InteractableButton(
                new MenuElementLocation(5,-45,40,10),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/refresh.png"),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/refresh_hover.png"),
                () ->{
                    setCurrentView(createAvailableGamesScreen());
                })
        );

        List<AvailableGame> availableGames = new StatusNetworkController().getAvailableGames(); //TODO: replace with call on this.networkmanager

        int startY = 35;
        int step = 12;
        int count = 6;
        for(int i = 0; i < count && i<availableGames.size(); i++ ){
            int y = startY - ( i * step);
            result.addButton(createJoinButton(
                    availableGames.get(i),
                    new MenuElementLocation(-20,y,40,10)
            ));
        }
        return result;
    }


    private void joinGame(AvailableGame gameInfo){
        System.out.println("clicked join on Game " + gameInfo.getId());
    }

    private InteractableButton createJoinButton(AvailableGame game, MenuElementLocation location){
        Texture background = new ImageTextureLoader().loadResource("textures/buttons/multiplayer/game.png");
        Texture backgroundHover = new ImageTextureLoader().loadResource("textures/buttons/multiplayer/game_hover.png");
        Texture texture = createGameTexture(game, Color.WHITE, background);
        Texture textureHover = createGameTexture(game, Color.YELLOW, backgroundHover);
        return new InteractableButton(location, texture, textureHover, () ->{
            joinGame(game);
        });
    }

    private Texture createGameTexture(AvailableGame game, Color color, Texture background){
        return TextTextureCreator.createTextureWithText(
                background,
                "SansSerif",
                List.of(
                        new TextInfo(game.getName(), 20,44, 40, color),
                        new TextInfo(formatGameDetails(game), 20,88, 40, color)
                )
        );
    }

    private String formatGameDetails(AvailableGame game){
        return "(" + game.getPlayerCount() + "/" + game.getMaxPlayerCount() + ") map: " + game.getMap() + " id: " + game.getId();
    }
}
