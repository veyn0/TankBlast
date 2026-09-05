package de.tankblast.protocol.packet.play;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;
import java.util.UUID;

@PacketId("de.tankblast.protocol.packet.play.playerstate.s2c")
public class ClientBoundPlayerStatePacket implements Packet {

    private UUID playerId;
    private double x, y, rotation;

    public ClientBoundPlayerStatePacket() {
    }

    public ClientBoundPlayerStatePacket(UUID playerId, double x, double y, double rotation) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuffer);
        playerId = DtoBufferUtil.readUUID(packetBuffer);
        x = packetBuffer.readDouble();
        y = packetBuffer.readDouble();
        rotation = packetBuffer.readDouble();
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        DtoBufferUtil.writeUUID(packetBuffer, playerId);
        packetBuffer.writeDouble(x);
        packetBuffer.writeDouble(y);
        packetBuffer.writeDouble(rotation);
        return packetBuffer.toByteBuffer();
    }
}
