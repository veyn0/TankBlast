package de.tankblast.protocol.dto.game.lobby;

import java.util.UUID;

public class startLobbyRequest {

    private UUID playerId;

    public startLobbyRequest(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
