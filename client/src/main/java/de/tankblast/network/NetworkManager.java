package de.tankblast.network;

import de.tankblast.protocol.registry.CommonPacketRegistry;
import xyz.wireway.frame.channel.ChannelSet;
import xyz.wireway.service.WireWay;
import xyz.wireway.service.packetstream.PacketStream;
import xyz.wireway.transport.adapter.SocketTransport;


public class NetworkManager {

    private PacketStream configurationPacketStream;

    private WireWay wireWay;

    private StatusNetworkController statusNetworkController;

    public NetworkManager(String host, int port){

//        wireWay = new WireWay(SocketTransport.connect(host,port), CommonPacketRegistry.create(), new ChannelSet(), 4096, 512);
//
//        this.configurationPacketStream = wireWay.createPacketChannel(0);
//

    }


    public PacketStream getConfigurationPacketStream() {
        return configurationPacketStream;
    }


    public StatusNetworkController getStatusNetworkController() {
            return new StatusNetworkController();
//        return statusNetworkController;
    }

}
