package de.tankblast.app;

import de.tankblast.menu.BackGround;
import de.tankblast.menu.element.InteractableButton;
import de.tankblast.menu.element.MenuElementLocation;
import de.tankblast.menu.menus.GenericMenu;
import de.tankblast.menu.menus.homescreen.HomeScreen;
import de.tankblast.protocol.dto.game.available.AvailableGame;
import de.tankblast.protocol.dto.game.lobby.LobbyInfo;
import de.tankblast.protocol.dto.player.PlayerInfo;
import de.tankblast.texture.ImageTextureLoader;
import de.tankblast.texture.PlayerUtils;
import de.tankblast.texture.Texture;

import java.awt.*;
import java.util.List;

public class LobbyScreenManager {

    private TankBlastClientApplication clientApplication;

    private LobbyInfo currentLobbyInfo;

    public LobbyScreenManager(TankBlastClientApplication clientApplication) {
        this.clientApplication = clientApplication;
    }

    public GenericMenu createLobbyScreen(LobbyInfo currentLobbyInfo){
        this.currentLobbyInfo = currentLobbyInfo;
        ImageTextureLoader loader = new ImageTextureLoader();
        GenericMenu result = new GenericMenu(new BackGround(new MenuElementLocation(-90, -50, 185, 100), loader.loadResource("textures/background/lobby.png")));

        if(currentLobbyInfo.getOwnerId()==clientApplication.getPlayerId()){
            //start button
            result.addButton(new InteractableButton(
                    new MenuElementLocation(-45,-30,40,10),
                    new ImageTextureLoader().loadResource("textures/buttons/multiplayer/start.png"),
                    new ImageTextureLoader().loadResource("textures/buttons/multiplayer/start_hover.png"),
                    () ->{
                        clientApplication.startGameSession();
                        System.out.println("game started");

                    })
            );

        }

        //back button
        result.addButton(new InteractableButton(
                new MenuElementLocation(-45,-45,40,10),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/back.png"),
                new ImageTextureLoader().loadResource("textures/buttons/multiplayer/back_hover.png"),
                () ->{
                    clientApplication.setCurrentView(new HomeScreen(clientApplication));
                })
        );

        result.addButton(createCreateButton(
                    new AvailableGame(currentLobbyInfo.getLobbyId(), currentLobbyInfo.getPlayers().size(), currentLobbyInfo.getMap(), currentLobbyInfo.getName() , currentLobbyInfo.getMaxPlayers()),
                    new MenuElementLocation(-45, 30, 40,10)
                )
        );

        List<PlayerInfo> playerInfos = currentLobbyInfo.getPlayers();
        int baseY = 35;
        int offsetY = -6;

        for(int i = 0; i < playerInfos.size(); i++){
            int y = baseY + (i * offsetY);
            Texture t = PlayerUtils.createPlayerInfoTexture(loader.loadResource("textures/buttons/multiplayer/game.png"), playerInfos.get(i));
            result.addButton(
                    new InteractableButton(
                            new MenuElementLocation(5,y,20,5),
                            t,
                            t,
                            () ->{

                            }
                    )
            );
        }

        return result;
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
