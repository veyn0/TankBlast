package de.tankblast.protocol.packet.play;

import de.tankblast.protocol.dto.game.lobby.startLobbyRequest;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.play.startlobby.c2s")
public class ServerBoundStartLobbyRequestPacket implements Packet {

    private startLobbyRequest request;

    public ServerBoundStartLobbyRequestPacket() {
    }

    public ServerBoundStartLobbyRequestPacket(startLobbyRequest request) {
        this.request = request;
    }

    public startLobbyRequest getRequest() {
        return request;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        request = startLobbyRequest.read(new PacketBuffer(byteBuffer));
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        packetBuffer.write(request);
        return packetBuffer.toByteBuffer();
    }
}
