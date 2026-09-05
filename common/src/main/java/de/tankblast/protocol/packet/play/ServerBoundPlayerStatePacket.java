package de.tankblast.protocol.packet.play;

import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.play.playerstate.c2s")
public class ServerBoundPlayerStatePacket implements Packet {

    private double x, y, rotation;

    public ServerBoundPlayerStatePacket() {
    }

    public ServerBoundPlayerStatePacket(double x, double y, double rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
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
        x = packetBuffer.readDouble();
        y = packetBuffer.readDouble();
        rotation = packetBuffer.readDouble();
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        packetBuffer.writeDouble(x);
        packetBuffer.writeDouble(y);
        packetBuffer.writeDouble(rotation);
        return packetBuffer.toByteBuffer();
    }
}
