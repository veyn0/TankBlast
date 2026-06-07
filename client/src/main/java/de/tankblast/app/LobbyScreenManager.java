package de.tankblast.app;

import de.tankblast.menu.menus.GenericMenu;
import de.tankblast.protocol.dto.game.lobby.LobbyInfo;

public class LobbyScreenManager {

    private TankBlastClientApplication clientApplication;

    private LobbyInfo currentLobbyInfo;

    public LobbyScreenManager(TankBlastClientApplication clientApplication) {
        this.clientApplication = clientApplication;
    }

    public GenericMenu createLobbyScreen(LobbyInfo currentLobbyInfo){
        this.currentLobbyInfo = currentLobbyInfo;
        GenericMenu result = new GenericMenu();
        return null;
    }

}
