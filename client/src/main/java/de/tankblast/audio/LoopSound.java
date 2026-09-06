package de.tankblast.audio;

public enum LoopSound {

    DRIVE("sounds/engine_loop.wav"),
    TURN("sounds/turn_loop.wav");

    private final String resourcePath;

    LoopSound(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
