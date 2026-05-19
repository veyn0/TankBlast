package de.tankblast.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TankBlastWindow extends JFrame implements BufferedImageDisplay {

    private final RenderPanel renderPanel;

    public TankBlastWindow(int displayWidth, int displayHeight) {
        renderPanel = new RenderPanel();
        renderPanel.setPreferredSize(new Dimension(displayWidth, displayHeight));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(renderPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void showImage(BufferedImage bufferedImage) {
        renderPanel.setFrame(bufferedImage);
        renderPanel.repaint();
    }

}