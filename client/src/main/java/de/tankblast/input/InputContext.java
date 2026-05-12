package de.tankblast.input;

import java.util.Map;

public class InputContext {

    private final Map<Integer, Long> keyStrokes;

    private final long timeSinceLastTickNanos;

    public InputContext(Map<Integer, Long> keyStrokes, long timeSinceLastTickNanos) {
        this.keyStrokes = keyStrokes;
        this.timeSinceLastTickNanos = timeSinceLastTickNanos;
    }

    public long getTimeSinceLastTickNanos() {
        return timeSinceLastTickNanos;
    }

    public Map<Integer, Long> getKeyStrokes() {
        return keyStrokes;
    }
}
