package de.tankblast.protocol.packet.configuration;

import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.configuration.start.s2c")
public class ClientBoundStartConfigurationResponsePacket implements Packet {

    @Override
    public void decode(ByteBuffer byteBuffer) {

    }

    @Override
    public ByteBuffer encode() {
        return null;
    }

}
