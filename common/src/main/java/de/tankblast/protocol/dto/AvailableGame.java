package de.tankblast.protocol.dto;

import xyz.wireway.service.BufferSerializable;
import xyz.wireway.service.PacketBuffer;

public class AvailableGame implements BufferSerializable {

    private int id, playerCount, map;
    private String name;

    public AvailableGame(int id, int playerCount, int map, String name) {
        this.id = id;
        this.playerCount = playerCount;
        this.map = map;
        this.name = name;
    }

    public int getId() {
        return id;
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
        packetBuffer.writeInt(id);
        packetBuffer.writeInt(playerCount);
        packetBuffer.writeInt(map);
        packetBuffer.writeString(name);
    }

    public static AvailableGame read(PacketBuffer packetBuffer){
        int id = packetBuffer.readInt();
        int playerCount = packetBuffer.readInt();
        int map = packetBuffer.readInt();
        String name = packetBuffer.readString();
        return new AvailableGame(id, playerCount, map, name);
    }

}
