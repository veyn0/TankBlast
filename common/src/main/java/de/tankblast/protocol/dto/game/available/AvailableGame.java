package de.tankblast.protocol.dto.game.available;

import xyz.wireway.service.BufferSerializable;
import xyz.wireway.service.PacketBuffer;

public class AvailableGame implements BufferSerializable {

    private int lobbyId, playerCount, maxPlayerCount, map;
    private String name;

    public AvailableGame(int lobbyId, int playerCount, int map, String name, int maxPlayerCount) {
        this.lobbyId = lobbyId;
        this.playerCount = playerCount;
        this.map = map;
        this.name = name;
        this.maxPlayerCount = maxPlayerCount;
    }

    public AvailableGame(){}

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeInt(lobbyId);
        packetBuffer.writeInt(playerCount);
        packetBuffer.writeInt(map);
        packetBuffer.writeInt(maxPlayerCount);
        packetBuffer.writeString(name);
    }

    public static AvailableGame read(PacketBuffer packetBuffer){
        int id = packetBuffer.readInt();
        int playerCount = packetBuffer.readInt();
        int map = packetBuffer.readInt();
        int maxPlayerCount = packetBuffer.readInt();
        String name = packetBuffer.readString();
        return new AvailableGame(id, playerCount, map, name, maxPlayerCount);
    }

}
