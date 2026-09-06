package de.tankblast.app;

import de.tankblast.audio.SoundManager;
import de.tankblast.input.GameMouseInputListener;
import de.tankblast.input.InputListener;
import de.tankblast.input.InputManager;
import de.tankblast.menu.Menu;
import de.tankblast.menu.MenuController;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.network.NetworkManager;
import de.tankblast.protocol.dto.player.PlayerInfo;
import de.tankblast.render.*;
import de.tankblast.texture.Colour;
import de.tankblast.view.TankBlastWindow;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TankBlastClientApplication {

    private UUID playerId = UUID.randomUUID();

    private TankBlastWindow window;
    private Renderer renderer;

    private int width = 1920;
    private int height = 1080;

    private Menu currentView;

    private MenuController menuController;

    private volatile boolean running = true;

    private NetworkManager networkManager;

    private AvailableGamesScreenManager availableGamesScreenManager;

    private CreateLobbyScreenManager createLobbyScreenManager;

    private LobbyScreenManager lobbyScreenManager;

    private InputManager inputManager;

    private GameSessionManager gameSessionManager;

    private SoundManager soundManager;

    public TankBlastClientApplication(){
        this.networkManager = new NetworkManager("localhost", 26656, playerId, this);
        this.availableGamesScreenManager = new AvailableGamesScreenManager(this);
        this.createLobbyScreenManager = new CreateLobbyScreenManager(this);
        this.lobbyScreenManager = new LobbyScreenManager(this);
        this.window = new TankBlastWindow(width, height);

        this.inputManager = new InputManager();
        window.addKeyInputListener(new InputListener(inputManager));
        window.addMouseInputListener(new GameMouseInputListener(inputManager));
        window.addFocusLossHandler(inputManager::onWindowOutOfFocus);

        this.soundManager = new SoundManager();

        PlayerCenteredCamera camera = new PlayerCenteredCamera();
        this.gameSessionManager = new GameSessionManager(this, inputManager, camera, width, height, soundManager);

        this.renderer = new VoxelRenderer();
        renderer.setCamera(camera);

        menuController = new MenuController(camera, width, height, soundManager);
        setCurrentView(new HomeScreen(this));
        window.addMouseInputListener(menuController);

        long time = System.currentTimeMillis();
        List<Integer> frametimes = new ArrayList<>();
        while (running){
            try {
                time = System.currentTimeMillis();
                renderer.clear(Colour.WHITE);
                gameSessionManager.updateCamera();
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

    public void startGameSession(int mapId, List<PlayerInfo> players){
        gameSessionManager.startGameSession(mapId, players);
    }

    public void stopGameSession(){
        gameSessionManager.stopGameSession();
    }

    public void onGameOver(UUID winnerId, String winnerName){
        stopGameSession();
        if (winnerId == null) {
            System.out.println("Atombombe! Alle Spieler haben verloren.");
        } else {
            System.out.println(winnerName + " has won the game!");
        }
        setCurrentView(new HomeScreen(this));
    }

    private void collectVoxels(){
        if (currentView != null) {
            for(Voxel v  : currentView.getVoxel()){
                renderer.add(v);
            }
        }
        for (GraphicsComponent component : gameSessionManager.getGraphicsComponents()) {
            renderer.add(component);
        }
    }

    public void exit(){
        this.running = false;
        gameSessionManager.stopGameSession();
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }

    public AvailableGamesScreenManager getAvailableGamesScreenManager() {
        return availableGamesScreenManager;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public CreateLobbyScreenManager getCreateLobbyScreenManager() {
        return createLobbyScreenManager;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public LobbyScreenManager getLobbyScreen() {
        return lobbyScreenManager;
    }

    public GameSessionManager getGameSessionManager() {
        return gameSessionManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}