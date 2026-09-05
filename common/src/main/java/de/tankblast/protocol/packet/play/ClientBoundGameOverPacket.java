package de.tankblast.protocol.packet.play;

import de.tankblast.protocol.dto.DtoBufferUtil;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;
import java.util.UUID;

@PacketId("de.tankblast.protocol.packet.play.gameover.s2c")
public class ClientBoundGameOverPacket implements Packet {

    private UUID winnerId;
    private String winnerName;

    public ClientBoundGameOverPacket() {
    }

    public ClientBoundGameOverPacket(UUID winnerId, String winnerName) {
        this.winnerId = winnerId;
        this.winnerName = winnerName;
    }

    public UUID getWinnerId() {
        return winnerId;
    }

    public String getWinnerName() {
        return winnerName;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuffer);
        winnerId = DtoBufferUtil.readUUID(packetBuffer);
        winnerName = packetBuffer.readString();
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        DtoBufferUtil.writeUUID(packetBuffer, winnerId);
        packetBuffer.writeString(winnerName);
        return packetBuffer.toByteBuffer();
    }
}
