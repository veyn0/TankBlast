package de.tankblast.texture.text;

import de.tankblast.texture.Texture;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

public class TextTextureCreator {


    public static Texture createTextureWithText(int width, int height, String font, List<TextInfo> textInfos) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        drawTexts(g, font, textInfos);
        g.dispose();
        return toTexture(img);
    }

    public static Texture createTextureWithText(Texture base, String font, List<TextInfo> textInfos) {
        int width = base.getWidth();
        int height = base.getHeight();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        img.setRGB(0, 0, width, height, base.getData(), 0, width);

        Graphics2D g = img.createGraphics();
        drawTexts(g, font, textInfos);
        g.dispose();
        return toTexture(img);
    }

    private static void drawTexts(Graphics2D g, String font, List<TextInfo> textInfos) {
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (TextInfo info : textInfos) {
            g.setFont(new Font(font, Font.PLAIN, info.getSize()));
            g.setColor(info.getColor());
            g.drawString(info.getText(), info.getX(), info.getY());
        }
    }

    private static Texture toTexture(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] data = new int[width * height];
        img.getRGB(0, 0, width, height, data, 0, width);
        return new Texture(data, width);
    }
}