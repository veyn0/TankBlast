package de.tankblast.protocol.packet.play;

import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.play.atombomb.c2s")
public class ServerBoundAtomBombPacket implements Packet {

    private int value = 0x4242;

    @Override
    public void decode(ByteBuffer byteBuffer) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuffer);
        value = packetBuffer.readInt();
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        packetBuffer.writeInt(value);
        return packetBuffer.toByteBuffer();
    }
}
