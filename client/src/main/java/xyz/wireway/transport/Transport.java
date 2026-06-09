package xyz.wireway.transport;

import xyz.wireway.transport.ConnectionState;
import xyz.wireway.transport.TransportListener;

import java.nio.ByteBuffer;

public interface Transport {

    void send(ByteBuffer data);

    void addListener(TransportListener listener);

    ConnectionState getConnectionState();

    void close();

}
