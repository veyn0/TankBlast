package de.tankblast.protocol.packet.play;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;
import java.util.UUID;

@PacketId("de.tankblast.protocol.packet.play.playerhit.c2s")
public class ServerBoundPlayerHitPacket implements Packet {

    private UUID targetPlayerId;

    public ServerBoundPlayerHitPacket() {
    }

    public ServerBoundPlayerHitPacket(UUID targetPlayerId) {
        this.targetPlayerId = targetPlayerId;
    }

    public UUID getTargetPlayerId() {
        return targetPlayerId;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        targetPlayerId = DtoBufferUtil.readUUID(new PacketBuffer(byteBuffer));
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        DtoBufferUtil.writeUUID(packetBuffer, targetPlayerId);
        return packetBuffer.toByteBuffer();
    }
}
