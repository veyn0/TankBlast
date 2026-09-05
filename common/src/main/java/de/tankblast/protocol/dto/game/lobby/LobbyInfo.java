package de.tankblast.protocol.dto.game.lobby;

import de.tankblast.protocol.dto.DtoBufferUtil;
import de.tankblast.protocol.dto.player.PlayerInfo;
import xyz.wireway.service.BufferSerializable;
import xyz.wireway.service.PacketBuffer;

import java.util.List;
import java.util.UUID;

public class LobbyInfo implements BufferSerializable {

    private String name;
    private int lobbyId, maxPlayers, map;
    private List<PlayerInfo> players;
    private UUID ownerId;

    public LobbyInfo(String name, int lobbyId, int maxPlayers, int map, List<PlayerInfo> players, UUID ownerId) {
        this.name = name;
        this.lobbyId = lobbyId;
        this.maxPlayers = maxPlayers;
        this.map = map;
        this.players = players;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMap() {
        return map;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeString(name);
        packetBuffer.writeInt(lobbyId);
        packetBuffer.writeInt(maxPlayers);
        packetBuffer.writeInt(map);
        packetBuffer.writeList(players);
        DtoBufferUtil.writeUUID(packetBuffer, ownerId);
    }

    public static LobbyInfo read(PacketBuffer packetBuffer){
        String name = packetBuffer.readString();
        int lobbyId = packetBuffer.readInt();
        int maxPlayers = packetBuffer.readInt();
        int map = packetBuffer.readInt();
        List<PlayerInfo> players = packetBuffer.readList(PlayerInfo::read);
        UUID ownerId = DtoBufferUtil.readUUID(packetBuffer);
        return new LobbyInfo(name, lobbyId, maxPlayers, map, players, ownerId);
    }
}
