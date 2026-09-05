package de.tankblast.protocol.packet.status;

import de.tankblast.protocol.dto.game.lobby.CreateLobbyRequest;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.status.createlobby.c2s")
public class ServerBoundCreateLobbyRequestPacket implements Packet {

    private CreateLobbyRequest request;

    public ServerBoundCreateLobbyRequestPacket() {
    }

    public ServerBoundCreateLobbyRequestPacket(CreateLobbyRequest request) {
        this.request = request;
    }

    public CreateLobbyRequest getRequest() {
        return request;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        request = CreateLobbyRequest.read(new PacketBuffer(byteBuffer));
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        packetBuffer.write(request);
        return packetBuffer.toByteBuffer();
    }
}
