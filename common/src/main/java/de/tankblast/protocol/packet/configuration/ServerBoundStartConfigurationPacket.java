package de.tankblast.protocol.packet.configuration;

import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.configuration.start.c2s")
public class ServerBoundStartConfigurationPacket implements Packet {

    private int clientVersion;

    @Override
    public void decode(ByteBuffer byteBuffer) {

    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeInt(clientVersion);
        return buffer.toByteBuffer();
    }
}
