package de.tankblast.protocol.dto.game.lobby;

import java.util.UUID;

public class CreateLobbyRequest {

    private UUID playerId;
    private String gameName;
    private int playerCount, map;

    public CreateLobbyRequest(UUID playerId, String gameName, int playerCount, int map) {
        this.playerId = playerId;
        this.gameName = gameName;
        this.playerCount = playerCount;
        this.map = map;
    }


    public UUID getPlayerId() {
        return playerId;
    }

    public String getGameName() {
        return gameName;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMap() {
        return map;
    }
}
