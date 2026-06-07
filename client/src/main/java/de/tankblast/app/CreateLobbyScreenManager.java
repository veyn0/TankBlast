package de.tankblast.app;

import de.tankblast.menu.BackGround;
import de.tankblast.menu.element.InteractableButton;
import de.tankblast.menu.element.MenuElementLocation;
import de.tankblast.menu.element.Slider;
import de.tankblast.menu.menus.GenericMenu;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.network.StatusNetworkController;
import de.tankblast.protocol.dto.game.available.AvailableGame;
import de.tankblast.protocol.dto.game.lobby.CreateLobbyRequest;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.MapUtils;
import de.tankblast.texture.Texture;

import java.awt.*;
import java.util.UUID;

public class CreateLobbyScreenManager {

    private TankBlastClientApplication clientApplication;

    public CreateLobbyScreenManager(TankBlastClientApplication clientApplication) {
        this.clientApplication = clientApplication;
    }

    private String name = "placeholderName";
    private int maxPlayers = 3;
    private int map = 0;

    public GenericMenu createCreateLobbyScreen(){
        ImageTextureLoader loader = new ImageTextureLoader();
        GenericMenu result = new GenericMenu(new BackGround(new MenuElementLocation(-90, -50, 185, 100), loader.loadResource("textures/background/create_lobby.png")));

        //back button
        result.addButton(new InteractableButton(
                new MenuElementLocation(-45,-45,40,10),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/back.png"),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/back_hover.png"),
                () ->{
                    clientApplication.setCurrentView(new HomeScreen(clientApplication));
                })
        );

        // max player slider
        Texture sliderTexture = loader.loadResource("textures/slider/slider.png");
        Texture sliderHeadTexture = loader.loadResource("textures/slider/slider_head.png");
        result.addElement(new Slider(new MenuElementLocation(-40,-10, 80,10), sliderTexture, sliderHeadTexture, 10, maxPlayers, i ->{
            maxPlayers = i;
            System.out.println("maxplayers: " + (i+1));
            clientApplication.setCurrentView(createCreateLobbyScreen());
        }));

        //preview
        result.addButton(createCreateButton(
                new AvailableGame(-1, 1, map, name, maxPlayers+1),
                new MenuElementLocation(-20,5,40,10)
        ));

        //map preview
        Texture mapPreview = MapUtils.createMapPreview(map);
        result.addButton(new InteractableButton(
                new MenuElementLocation(-20,20,40,10),
                mapPreview,
                mapPreview,
                () ->{
                    map++;
                    if(map>2) map = 0;
                    clientApplication.setCurrentView(createCreateLobbyScreen());
                })
        );

        //create button
        result.addButton(new InteractableButton(
                new MenuElementLocation(5,-45,40,10),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/create.png"),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/create_hover.png"),
                this::onGameCreate
                )
        );
        return result;
    }

    private void onGameCreate(){
        System.out.println("click game create");
        StatusNetworkController networkController = clientApplication.getNetworkManager().getStatusNetworkController();
        networkController.createLobby(
                new CreateLobbyRequest(clientApplication.getPlayerId(), name, maxPlayers, map),
                lobbyInfo -> {
                    clientApplication.setCurrentView(clientApplication.getLobbyScreen().createLobbyScreen(lobbyInfo));
                }
        );
    }

    private InteractableButton createCreateButton(AvailableGame game, MenuElementLocation location){
        Texture background = new ImageTextureLoader().loadResource("textures/buttons/multiplayer/game.png");
        Texture backgroundHover = new ImageTextureLoader().loadResource("textures/buttons/multiplayer/game_hover.png");
        Texture texture = AvailableGamesScreenManager.createGameTexture(game, Color.WHITE, background);
        Texture textureHover = AvailableGamesScreenManager.createGameTexture(game, Color.WHITE, backgroundHover);
        return new InteractableButton(location, texture, textureHover, () ->{

        });
    }

}
