package de.tankblast.protocol.dto.game.lobby;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.service.BufferSerializable;
import xyz.wireway.service.PacketBuffer;

import java.util.UUID;

public class JoinLobbyRequest implements BufferSerializable {

    private UUID playerId;
    private int lobbyId;

    public JoinLobbyRequest(UUID playerId, int lobbyId) {
        this.playerId = playerId;
        this.lobbyId = lobbyId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        DtoBufferUtil.writeUUID(packetBuffer, playerId);
        packetBuffer.writeInt(lobbyId);
    }

    public static JoinLobbyRequest read(PacketBuffer packetBuffer){
        UUID playerId = DtoBufferUtil.readUUID(packetBuffer);
        int lobbyId = packetBuffer.readInt();
        return new JoinLobbyRequest(playerId, lobbyId);
    }
}
