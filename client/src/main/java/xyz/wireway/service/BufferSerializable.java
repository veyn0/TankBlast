package xyz.wireway.service;

import xyz.wireway.service.PacketBuffer;

public interface BufferSerializable {
    void write(PacketBuffer buf);
}
