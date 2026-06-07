package de.tankblast.protocol.dto.player;

import java.util.UUID;

public class PlayerInfo {

    private UUID playerId;
    private String name;

    public PlayerInfo(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public UUID getPlayerId() {
        return playerId;
    }
}
