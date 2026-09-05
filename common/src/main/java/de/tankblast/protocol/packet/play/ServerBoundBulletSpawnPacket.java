package de.tankblast.protocol.packet.play;

import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.play.bulletspawn.c2s")
public class ServerBoundBulletSpawnPacket implements Packet {

    private double x, y, dirX, dirY;

    public ServerBoundBulletSpawnPacket() {
    }

    public ServerBoundBulletSpawnPacket(double x, double y, double dirX, double dirY) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
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
        x = packetBuffer.readDouble();
        y = packetBuffer.readDouble();
        dirX = packetBuffer.readDouble();
        dirY = packetBuffer.readDouble();
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        packetBuffer.writeDouble(x);
        packetBuffer.writeDouble(y);
        packetBuffer.writeDouble(dirX);
        packetBuffer.writeDouble(dirY);
        return packetBuffer.toByteBuffer();
    }
}
