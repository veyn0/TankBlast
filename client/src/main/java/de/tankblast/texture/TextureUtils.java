package de.tankblast.texture;

public class TextureUtils {

    public static Texture tint(Texture base, int colour) {
        int[] source = base.getData();
        int[] result = new int[source.length];

        int tintR = (colour >> 16) & 0xFF;
        int tintG = (colour >> 8) & 0xFF;
        int tintB = colour & 0xFF;

        for (int i = 0; i < source.length; i++) {
            int pixel = source[i];
            int alpha = pixel >>> 24;
            if (alpha == 0) {
                result[i] = 0;
                continue;
            }

            int r = (pixel >> 16) & 0xFF;
            int g = (pixel >> 8) & 0xFF;
            int b = pixel & 0xFF;
            double luminance = (r * 0.299 + g * 0.587 + b * 0.114) / 255.0;

            int newR = (int) Math.round(luminance * tintR);
            int newG = (int) Math.round(luminance * tintG);
            int newB = (int) Math.round(luminance * tintB);

            result[i] = (alpha << 24) | (newR << 16) | (newG << 8) | newB;
        }

        return new Texture(result, base.getWidth());
    }

}
