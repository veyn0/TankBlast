package de.tankblast.texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageTextureLoader {

    public Texture loadResource(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + path);
            }
            BufferedImage image = ImageIO.read(is);
            if (image == null) {
                throw new IllegalArgumentException("Could not decode image: " + path);
            }
            int width = image.getWidth();
            int height = image.getHeight();
            int[] data = new int[width * height];
            image.getRGB(0, 0, width, height, data, 0, width);
            return new Texture(data, width);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load texture: " + path, e);
        }
    }
}