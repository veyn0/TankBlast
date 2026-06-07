package de.tankblast.network;

import de.tankblast.protocol.dto.game.available.AvailableGame;
import de.tankblast.protocol.dto.game.lobby.CreateLobbyRequest;
import de.tankblast.protocol.dto.game.lobby.LobbyInfo;
import de.tankblast.protocol.dto.player.PlayerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class StatusNetworkController {

    public List<AvailableGame> getAvailableGames(){

        //TODO: implement real networking

        return List.of(new AvailableGame(0, 0, 3 ,"PlaceholderGame", 8));
    }

    public void createLobby(CreateLobbyRequest request, Consumer<LobbyInfo> lobbyInfoConsumer){

        //TODO: implement server communication

        // consumer also has to be accepted for updating current view of lobby

        lobbyInfoConsumer.accept(new LobbyInfo(request.getGameName(), 1, request.getPlayerCount(), request.getMap(), createPlaceholderInfo(), request.getPlayerId()));



    }

    public static List<PlayerInfo> createPlaceholderInfo(){
        List<PlayerInfo> result = new ArrayList<>();
        for(int i = 0; i < (5+(Math.random()*5)); i++){
            result.add(new PlayerInfo(UUID.randomUUID(), "Player "+i));
        }
        return result;
    }

    public void requestJoinLobby(int lobbyId, Consumer<LobbyInfo> lobbyInfoConsumer){


    }

}
