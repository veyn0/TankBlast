package de.tankblast.protocol.packet.status;


import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.status.availablegames.c2s")
public class ServerBoundAvailableGameRequest implements Packet {

    private int value = 0x2222;

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
