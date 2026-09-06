package de.tankblast.protocol.packet.play;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;
import java.util.UUID;

@PacketId("de.tankblast.protocol.packet.play.playereliminated.s2c")
public class ClientBoundPlayerEliminatedPacket implements Packet {

    private UUID playerId;

    public ClientBoundPlayerEliminatedPacket() {
    }

    public ClientBoundPlayerEliminatedPacket(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        playerId = DtoBufferUtil.readUUID(new PacketBuffer(byteBuffer));
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        DtoBufferUtil.writeUUID(packetBuffer, playerId);
        return packetBuffer.toByteBuffer();
    }
}
