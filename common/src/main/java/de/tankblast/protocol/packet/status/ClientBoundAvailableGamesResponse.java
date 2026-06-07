package de.tankblast.protocol.packet.status;

import de.tankblast.protocol.dto.AvailableGame;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.BufferSerializable;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;
import java.util.List;

@PacketId("de.tankblast.protocol.packet.status.availablegames.s2c")
public class ClientBoundAvailableGamesResponse implements Packet {

    private List<AvailableGame> availableGames;

    public ClientBoundAvailableGamesResponse(List<AvailableGame> availableGames) {
        this.availableGames = availableGames;
    }

    public List<AvailableGame> getAvailableGames() {
        return availableGames;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuffer);
        packetBuffer.readList(AvailableGame::read);

    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeList(availableGames);
        return buffer.toByteBuffer();
    }

}
