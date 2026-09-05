package de.tankblast.protocol.packet.status;

import de.tankblast.protocol.dto.game.lobby.JoinLobbyRequest;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.status.joinlobby.c2s")
public class ServerBoundJoinLobbyRequestPacket implements Packet {

    private JoinLobbyRequest request;

    public ServerBoundJoinLobbyRequestPacket() {
    }

    public ServerBoundJoinLobbyRequestPacket(JoinLobbyRequest request) {
        this.request = request;
    }

    public JoinLobbyRequest getRequest() {
        return request;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        request = JoinLobbyRequest.read(new PacketBuffer(byteBuffer));
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        packetBuffer.write(request);
        return packetBuffer.toByteBuffer();
    }
}
