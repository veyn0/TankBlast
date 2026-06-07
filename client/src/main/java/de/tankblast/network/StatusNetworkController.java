package de.tankblast.network;

import de.tankblast.protocol.dto.game.available.AvailableGame;
import de.tankblast.protocol.dto.game.lobby.CreateLobbyRequest;
import de.tankblast.protocol.dto.game.lobby.LobbyInfo;

import java.util.List;
import java.util.function.Consumer;

public class StatusNetworkController {

    public List<AvailableGame> getAvailableGames(){

        //TODO: implement real networking

        return List.of(new AvailableGame(0, 0, 3 ,"PlaceholderGame", 8));
    }

    public void createLobby(CreateLobbyRequest request, Consumer<LobbyInfo> lobbyInfoConsumer){




    }

    public void requestJoinLobby(int lobbyId, Consumer<LobbyInfo> lobbyInfoConsumer){


    }

}
