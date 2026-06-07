package de.tankblast.protocol.registry;

import de.tankblast.protocol.packet.configuration.ClientBoundStartConfigurationResponsePacket;
import de.tankblast.protocol.packet.configuration.ServerBoundStartConfigurationPacket;
import xyz.wireway.protocol.PacketRegistry;
import xyz.wireway.protocol.Protocol;

public class CommonPacketRegistry {

    public static PacketRegistry create(){
        Protocol protocol = new Protocol();

        protocol.register(ClientBoundStartConfigurationResponsePacket.class);
        protocol.register(ServerBoundStartConfigurationPacket.class);

        return new PacketRegistry(protocol);
    }

}
