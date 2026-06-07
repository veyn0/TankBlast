package de.tankblast.app;

import de.tankblast.menu.BackGround;
import de.tankblast.menu.element.InteractableButton;
import de.tankblast.menu.element.MenuElementLocation;
import de.tankblast.menu.menus.GenericMenu;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.network.StatusNetworkController;
import de.tankblast.protocol.dto.game.available.AvailableGame;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.Texture;
import de.tankblast.texture.text.TextInfo;
import de.tankblast.texture.text.TextTextureCreator;

import java.awt.*;
import java.util.List;

public class AvailableGamesScreenManager{

    private TankBlastClientApplication clientApplication;

    public AvailableGamesScreenManager(TankBlastClientApplication clientApplication) {
        this.clientApplication = clientApplication;
    }

    public GenericMenu createAvailableGamesScreen(){
        ImageTextureLoader loader = new ImageTextureLoader();
        GenericMenu result = new GenericMenu(new BackGround(new MenuElementLocation(-90, -50, 185, 100), loader.loadResource("textures/background/games.png")));

        //back button
        result.addButton(new InteractableButton(
                new MenuElementLocation(-65,-45,40,10),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/back.png"),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/back_hover.png"),
                () ->{
                    clientApplication.setCurrentView(new HomeScreen(clientApplication));
                })
        );

        //refresh button
        result.addButton(new InteractableButton(
                new MenuElementLocation(-20,-45,40,10),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/refresh.png"),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/refresh_hover.png"),
                () ->{
                    clientApplication.setCurrentView(createAvailableGamesScreen());
                })
        );


        //create button
        result.addButton(new InteractableButton(
                new MenuElementLocation(25,-45,40,10),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/create.png"),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/create_hover.png"),
                () ->{
                    clientApplication.setCurrentView(clientApplication.getCreateLobbyScreenManager().createCreateLobbyScreen());
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
        System.out.println("clicked join on Game " + gameInfo.getLobbyId());
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

    public static Texture createGameTexture(AvailableGame game, Color color, Texture background){
        return TextTextureCreator.createTextureWithText(
                background,
                "SansSerif",
                List.of(
                        new TextInfo(game.getName(), 20,44, 40, color),
                        new TextInfo(formatGameDetails(game), 20,88, 40, color)
                )
        );
    }

    public static String formatGameDetails(AvailableGame game){
        return "(" + game.getPlayerCount() + "/" + game.getMaxPlayerCount() + ") map: " + game.getMap() + " id: " + game.getLobbyId();
    }

}
