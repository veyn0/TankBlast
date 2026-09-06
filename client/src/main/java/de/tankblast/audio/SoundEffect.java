package de.tankblast.audio;

public enum SoundEffect {

    MENU_CLICK("sounds/menu_click.wav"),
    BULLET_BOUNCE("sounds/bullet_bounce.wav"),
    BULLET_COLLISION("sounds/bullet_collision.wav"),
    HIT("sounds/hit.wav"),
    DEATH("sounds/death.wav");

    private final String resourcePath;

    SoundEffect(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
