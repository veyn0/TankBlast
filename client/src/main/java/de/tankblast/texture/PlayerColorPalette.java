package de.tankblast.texture;

public class PlayerColorPalette {

    private static final int[] COLOURS = {
            0xFFE6194B,
            0xFF3CB44B,
            0xFF4363D8,
            0xFFF58231,
            0xFF911EB4,
            0xFF46F0F0,
            0xFFF032E6,
            0xFFFFE119
    };

    public static int colourFor(int index) {
        return COLOURS[Math.floorMod(index, COLOURS.length)];
    }

}
