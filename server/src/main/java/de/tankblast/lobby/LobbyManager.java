package de.tankblast.lobby;

import de.tankblast.network.ClientSession;
import de.tankblast.protocol.dto.game.available.AvailableGame;
import de.tankblast.protocol.dto.game.lobby.CreateLobbyRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LobbyManager {

    private final Map<Integer, Lobby> lobbies = new ConcurrentHashMap<>();

    private final AtomicInteger lobbyIdAllocator = new AtomicInteger(1);

    public Lobby createLobby(ClientSession owner, CreateLobbyRequest request){
        int id = lobbyIdAllocator.getAndIncrement();
        Lobby lobby = new Lobby(id, request.getGameName(), request.getPlayerCount(), request.getMap(), request.getPlayerId());
        lobby.addPlayer(owner);
        lobbies.put(id, lobby);
        return lobby;
    }

    public Lobby getLobby(int lobbyId){
        return lobbies.get(lobbyId);
    }

    public void removeLobby(int lobbyId){
        lobbies.remove(lobbyId);
    }

    public List<AvailableGame> listAvailableGames(){
        List<AvailableGame> result = new ArrayList<>();
        for (Lobby lobby : lobbies.values()) {
            if (!lobby.isStarted()) result.add(lobby.toAvailableGame());
        }
        return result;
    }

}
