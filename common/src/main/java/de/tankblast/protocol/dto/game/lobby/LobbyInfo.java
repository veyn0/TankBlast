package de.tankblast.protocol.dto.game.lobby;

import de.tankblast.protocol.dto.player.PlayerInfo;

import java.util.List;
import java.util.UUID;

public class LobbyInfo {

    private String name;
    private int lobbyId, maxPlayers, map;
    private List<PlayerInfo> players;
    private UUID ownerId;

    public LobbyInfo(String name, int lobbyId, int maxPlayers, int map, List<PlayerInfo> players, UUID ownerId) {
        this.name = name;
        this.lobbyId = lobbyId;
        this.maxPlayers = maxPlayers;
        this.map = map;
        this.players = players;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMap() {
        return map;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public UUID getOwnerId() {
        return ownerId;
    }
}
