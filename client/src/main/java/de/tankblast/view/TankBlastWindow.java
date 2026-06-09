package de.tankblast.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

public class TankBlastWindow extends JFrame implements BufferedImageDisplay {

    private final RenderPanel renderPanel;

    public TankBlastWindow(int displayWidth, int displayHeight) {
        renderPanel = new RenderPanel();
        renderPanel.setPreferredSize(new Dimension(displayWidth, displayHeight));

        renderPanel.setFocusable(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(renderPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        renderPanel.requestFocusInWindow();
    }

    @Override
    public void showImage(BufferedImage bufferedImage) {
        renderPanel.setFrame(bufferedImage);
        renderPanel.repaint();
    }

    public void addMouseInputListener(MouseAdapter listener) {
        renderPanel.addMouseListener(listener);
        renderPanel.addMouseMotionListener(listener);
    }

    public void addKeyInputListener(KeyListener listener) {
        renderPanel.addKeyListener(listener);
    }

    public void addFocusLossHandler(Runnable onFocusLost) {
        renderPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                onFocusLost.run();
            }
        });
    }
}