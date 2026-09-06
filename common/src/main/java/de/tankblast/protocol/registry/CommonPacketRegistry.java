package de.tankblast.protocol.registry;

import de.tankblast.protocol.packet.configuration.ClientBoundStartConfigurationResponsePacket;
import de.tankblast.protocol.packet.configuration.ServerBoundStartConfigurationPacket;
import de.tankblast.protocol.packet.play.ClientBoundBulletSpawnPacket;
import de.tankblast.protocol.packet.play.ClientBoundGameOverPacket;
import de.tankblast.protocol.packet.play.ClientBoundInitGamePacket;
import de.tankblast.protocol.packet.play.ClientBoundPlayerEliminatedPacket;
import de.tankblast.protocol.packet.play.ClientBoundPlayerStatePacket;
import de.tankblast.protocol.packet.play.ServerBoundBulletSpawnPacket;
import de.tankblast.protocol.packet.play.ServerBoundPlayerHitPacket;
import de.tankblast.protocol.packet.play.ServerBoundPlayerStatePacket;
import de.tankblast.protocol.packet.play.ServerBoundStartLobbyRequestPacket;
import de.tankblast.protocol.packet.status.ClientBoundAvailableGamesResponse;
import de.tankblast.protocol.packet.status.ClientBoundLobbyInfoResponsePacket;
import de.tankblast.protocol.packet.status.ClientBoundLobbyUpdatePacket;
import de.tankblast.protocol.packet.status.ServerBoundAvailableGameRequest;
import de.tankblast.protocol.packet.status.ServerBoundCreateLobbyRequestPacket;
import de.tankblast.protocol.packet.status.ServerBoundJoinLobbyRequestPacket;
import xyz.wireway.protocol.PacketRegistry;
import xyz.wireway.protocol.Protocol;

public class CommonPacketRegistry {

    public static PacketRegistry create(){
        Protocol protocol = new Protocol();

        protocol.register(ClientBoundStartConfigurationResponsePacket.class);
        protocol.register(ServerBoundStartConfigurationPacket.class);

        protocol.register(ServerBoundAvailableGameRequest.class);
        protocol.register(ClientBoundAvailableGamesResponse.class);
        protocol.register(ServerBoundCreateLobbyRequestPacket.class);
        protocol.register(ServerBoundJoinLobbyRequestPacket.class);
        protocol.register(ClientBoundLobbyInfoResponsePacket.class);
        protocol.register(ClientBoundLobbyUpdatePacket.class);

        protocol.register(ServerBoundStartLobbyRequestPacket.class);
        protocol.register(ClientBoundInitGamePacket.class);
        protocol.register(ServerBoundPlayerStatePacket.class);
        protocol.register(ClientBoundPlayerStatePacket.class);
        protocol.register(ServerBoundBulletSpawnPacket.class);
        protocol.register(ClientBoundBulletSpawnPacket.class);
        protocol.register(ServerBoundPlayerHitPacket.class);
        protocol.register(ClientBoundPlayerEliminatedPacket.class);
        protocol.register(ClientBoundGameOverPacket.class);

        return new PacketRegistry(protocol);
    }

}
