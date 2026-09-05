package de.tankblast.protocol.packet.status;

import de.tankblast.protocol.dto.game.lobby.LobbyInfo;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;

@PacketId("de.tankblast.protocol.packet.status.lobbyinfo.s2c")
public class ClientBoundLobbyInfoResponsePacket implements Packet {

    private LobbyInfo lobbyInfo;

    public ClientBoundLobbyInfoResponsePacket() {
    }

    public ClientBoundLobbyInfoResponsePacket(LobbyInfo lobbyInfo) {
        this.lobbyInfo = lobbyInfo;
    }

    public LobbyInfo getLobbyInfo() {
        return lobbyInfo;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        lobbyInfo = LobbyInfo.read(new PacketBuffer(byteBuffer));
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        packetBuffer.write(lobbyInfo);
        return packetBuffer.toByteBuffer();
    }
}
