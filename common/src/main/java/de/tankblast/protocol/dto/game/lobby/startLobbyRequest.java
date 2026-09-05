package de.tankblast.protocol.dto.game.lobby;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.service.BufferSerializable;
import xyz.wireway.service.PacketBuffer;

import java.util.UUID;

public class startLobbyRequest implements BufferSerializable {

    private UUID playerId;

    public startLobbyRequest(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        DtoBufferUtil.writeUUID(packetBuffer, playerId);
    }

    public static startLobbyRequest read(PacketBuffer packetBuffer){
        return new startLobbyRequest(DtoBufferUtil.readUUID(packetBuffer));
    }
}
