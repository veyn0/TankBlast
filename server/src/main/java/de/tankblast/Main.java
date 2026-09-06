package de.tankblast;

import de.tankblast.lobby.LobbyManager;
import de.tankblast.network.ClientSession;
import de.tankblast.protocol.registry.CommonPacketRegistry;
import xyz.wireway.channel.ChannelSet;
import xyz.wireway.service.WireWay;
import xyz.wireway.transport.TransportListener;
import xyz.wireway.transport.adapter.SocketTransport;

import java.nio.ByteBuffer;

public class Main {

    public static void main(String[] args) {
        LobbyManager lobbyManager = new LobbyManager();

        SocketTransport.listen(26656, transport -> {
            WireWay wireWay = new WireWay(transport, CommonPacketRegistry.create(), new ChannelSet(), 4096, 512);
            ClientSession session = new ClientSession(wireWay, lobbyManager);
            session.register();
            transport.addListener(new TransportListener() {
                @Override
                public void onReceive(ByteBuffer data) {}

                @Override
                public void onClosed() {
                    session.onDisconnect();
                }
            });
        });
    }

}