package de.tankblast.protocol.packet.play;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;
import java.util.UUID;

@PacketId("de.tankblast.protocol.packet.play.playerlives.s2c")
public class ClientBoundPlayerLivesPacket implements Packet {

    private UUID playerId;
    private int lives;

    public ClientBoundPlayerLivesPacket() {
    }

    public ClientBoundPlayerLivesPacket(UUID playerId, int lives) {
        this.playerId = playerId;
        this.lives = lives;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getLives() {
        return lives;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuffer);
        playerId = DtoBufferUtil.readUUID(packetBuffer);
        lives = packetBuffer.readInt();
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        DtoBufferUtil.writeUUID(packetBuffer, playerId);
        packetBuffer.writeInt(lives);
        return packetBuffer.toByteBuffer();
    }
}
