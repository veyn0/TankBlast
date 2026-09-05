package de.tankblast.protocol.packet.play;

import de.tankblast.protocol.dto.player.PlayerInfo;
import xyz.wireway.protocol.Packet;
import xyz.wireway.protocol.PacketId;
import xyz.wireway.service.PacketBuffer;

import java.nio.ByteBuffer;
import java.util.List;

@PacketId("de.tankblast.protocol.packet.play.initgame.s2c")
public class ClientBoundInitGamePacket implements Packet {

    private int map;
    private List<PlayerInfo> players;

    public ClientBoundInitGamePacket() {
    }

    public ClientBoundInitGamePacket(int map, List<PlayerInfo> players) {
        this.map = map;
        this.players = players;
    }

    public int getMap() {
        return map;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuffer);
        map = packetBuffer.readInt();
        players = packetBuffer.readList(PlayerInfo::read);
    }

    @Override
    public ByteBuffer encode() {
        PacketBuffer packetBuffer = new PacketBuffer();
        packetBuffer.writeInt(map);
        packetBuffer.writeList(players);
        return packetBuffer.toByteBuffer();
    }
}
