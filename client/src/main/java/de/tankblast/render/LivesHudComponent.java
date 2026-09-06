package de.tankblast.render;

import java.util.ArrayList;
import java.util.List;

public class LivesHudComponent implements GraphicsComponent {

    private static final double DOT_PIXEL_SIZE = 22;
    private static final double DOT_PIXEL_SPACING = 34;
    private static final double TOP_MARGIN_PIXELS = 30;

    private static final int FILLED_COLOUR = 0xFFE6194B;
    private static final int EMPTY_COLOUR = 0xFF555555;

    private final Camera camera;
    private final int screenWidth;
    private final int screenHeight;
    private final int lives;
    private final int maxLives;

    public LivesHudComponent(Camera camera, int screenWidth, int screenHeight, int lives, int maxLives) {
        this.camera = camera;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.lives = lives;
        this.maxLives = maxLives;
    }

    @Override
    public List<Voxel> getVoxel() {
        List<Voxel> result = new ArrayList<>(maxLives);
        double pixelPerUnit = screenHeight / (2.0 * camera.getPosition().getZ());
        double cameraX = camera.getPosition().getX();
        double cameraY = camera.getPosition().getY();

        double totalWidth = (maxLives - 1) * DOT_PIXEL_SPACING;
        double startScreenX = screenWidth / 2.0 - totalWidth / 2.0;

        for (int i = 0; i < maxLives; i++) {
            double screenX = startScreenX + i * DOT_PIXEL_SPACING;
            double worldX = cameraX + (screenX - screenWidth / 2.0) / pixelPerUnit;
            double worldY = cameraY - (TOP_MARGIN_PIXELS - screenHeight / 2.0) / pixelPerUnit;
            double worldSize = DOT_PIXEL_SIZE / pixelPerUnit;
            int colour = i < lives ? FILLED_COLOUR : EMPTY_COLOUR;
            result.add(new Voxel(colour, worldX, worldY, worldSize, 0));
        }
        return result;
    }
}
