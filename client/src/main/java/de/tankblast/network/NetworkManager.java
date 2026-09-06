package de.tankblast.network;

import de.tankblast.app.TankBlastClientApplication;
import de.tankblast.protocol.registry.CommonPacketRegistry;
import xyz.wireway.channel.ChannelSet;
import xyz.wireway.service.PacketStream;
import xyz.wireway.service.WireWay;
import xyz.wireway.transport.adapter.SocketTransport;

import java.util.UUID;

public class NetworkManager {

    private final WireWay wireWay;

    private final PacketStream configurationPacketStream;

    private final StatusNetworkController statusNetworkController;

    private final GameNetworkController gameNetworkController;

    public NetworkManager(String host, int port, UUID playerId, TankBlastClientApplication clientApplication){
        wireWay = new WireWay(SocketTransport.connect(host, port), CommonPacketRegistry.create(), new ChannelSet(), 4096, 512);

        this.configurationPacketStream = wireWay.createPacketChannel(0);

        this.statusNetworkController = new StatusNetworkController(configurationPacketStream, playerId);
        this.gameNetworkController = new GameNetworkController(wireWay, playerId, clientApplication);
    }


    public PacketStream getConfigurationPacketStream() {
        return configurationPacketStream;
    }


    public StatusNetworkController getStatusNetworkController() {
        return statusNetworkController;
    }

    public GameNetworkController getGameNetworkController() {
        return gameNetworkController;
    }

}
