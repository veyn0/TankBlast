package de.tankblast.protocol.dto.game.lobby;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.service.BufferSerializable;
import xyz.wireway.service.PacketBuffer;

import java.util.UUID;

public class CreateLobbyRequest implements BufferSerializable {

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

    @Override
    public void write(PacketBuffer packetBuffer) {
        DtoBufferUtil.writeUUID(packetBuffer, playerId);
        packetBuffer.writeString(gameName);
        packetBuffer.writeInt(playerCount);
        packetBuffer.writeInt(map);
    }

    public static CreateLobbyRequest read(PacketBuffer packetBuffer){
        UUID playerId = DtoBufferUtil.readUUID(packetBuffer);
        String gameName = packetBuffer.readString();
        int playerCount = packetBuffer.readInt();
        int map = packetBuffer.readInt();
        return new CreateLobbyRequest(playerId, gameName, playerCount, map);
    }
}
