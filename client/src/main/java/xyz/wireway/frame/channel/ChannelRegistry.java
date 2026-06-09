package xyz.wireway.frame.channel;

import xyz.wireway.frame.channel.Channel;
import xyz.wireway.frame.channel.ChannelSet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ChannelRegistry {

    private final Map<Integer, Supplier<xyz.wireway.frame.channel.Channel<?>>> incomingChannelRegistry = new ConcurrentHashMap<>();
    private final Map<Class<? extends xyz.wireway.frame.channel.Channel<?>>, Integer> channelIdsByClass = new ConcurrentHashMap<>();
    private final Map<Integer, Object> contextsById = new ConcurrentHashMap<>();

    public ChannelRegistry(xyz.wireway.frame.channel.ChannelSet... sets) {
        Map<Integer, xyz.wireway.frame.channel.ChannelSet.ChannelEntry> registry = buildRegistry(sets);
        for (int id : registry.keySet()) {
            xyz.wireway.frame.channel.ChannelSet.ChannelEntry entry = registry.get(id);
            Class<? extends xyz.wireway.frame.channel.Channel<?>> type = entry.type();

            incomingChannelRegistry.put(id, () -> {
                try {
                    return type.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            channelIdsByClass.put(type, id);
            contextsById.put(id, entry.context());
        }
    }

    public int getChannelId(xyz.wireway.frame.channel.Channel<?> channel) {
        return channelIdsByClass.get(channel.getClass());
    }

    @SuppressWarnings("unchecked")
    public xyz.wireway.frame.channel.Channel<?> createChannel(int id) {
        Supplier<xyz.wireway.frame.channel.Channel<?>> supplier = incomingChannelRegistry.get(id);
        if (supplier == null) throw new IllegalArgumentException("Channel id " + id + " not registered");

        xyz.wireway.frame.channel.Channel<Object> channel = (Channel<Object>) supplier.get();
        channel.inject(contextsById.get(id));
        return channel;
    }

    private static Map<Integer, xyz.wireway.frame.channel.ChannelSet.ChannelEntry> buildRegistry(xyz.wireway.frame.channel.ChannelSet... sets) {
        Map<Long, xyz.wireway.frame.channel.ChannelSet.ChannelEntry> combined = new HashMap<>();
        for (xyz.wireway.frame.channel.ChannelSet set : sets) {
            combined.putAll(set.getNamedChannelRegistry());
        }
        List<Long> sortedHashes = new ArrayList<>(combined.keySet());
        Collections.sort(sortedHashes);
        Map<Integer, ChannelSet.ChannelEntry> result = new LinkedHashMap<>();
        for (int i = 0; i < sortedHashes.size(); i++) {
            result.put(i, combined.get(sortedHashes.get(i)));
        }
        return result;
    }
}