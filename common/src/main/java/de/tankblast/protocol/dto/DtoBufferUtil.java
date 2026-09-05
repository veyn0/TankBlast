package de.tankblast.protocol.dto;

import xyz.wireway.service.PacketBuffer;

import java.util.UUID;

public class DtoBufferUtil {

    public static void writeUUID(PacketBuffer buffer, UUID id){
        buffer.writeLong(id.getMostSignificantBits());
        buffer.writeLong(id.getLeastSignificantBits());
    }

    public static UUID readUUID(PacketBuffer buffer){
        return new UUID(buffer.readLong(), buffer.readLong());
    }

}
