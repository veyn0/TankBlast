package de.tankblast.protocol.packet.play;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;
import java.util.UUID;

@PacketId("de.tankblast.protocol.packet.play.bulletspawn.s2c")
public class ClientBoundBulletSpawnPacket implements Packet {

    private UUID shooterId;
    private double x, y, dirX, dirY;

    public ClientBoundBulletSpawnPacket() {
    }

    public ClientBoundBulletSpawnPacket(UUID shooterId, double x, double y, double dirX, double dirY) {
        this.shooterId = shooterId;
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public UUID getShooterId() {
        return shooterId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirX() {
        return dirX;
    }

    public double getDirY() {
        return dirY;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuffer);
        shooterId = DtoBufferUtil.readUUID(packetBuffer);
        x = packetBuffer.readDouble();
        y = packetBuffer.readDouble();
        dirX = packetBuffer.readDouble();
        dirY = packetBuffer.readDouble();
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        DtoBufferUtil.writeUUID(packetBuffer, shooterId);
        packetBuffer.writeDouble(x);
        packetBuffer.writeDouble(y);
        packetBuffer.writeDouble(dirX);
        packetBuffer.writeDouble(dirY);
        return packetBuffer.toByteBuffer();
    }
}
