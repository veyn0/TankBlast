package de.tankblast.render;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TankBlastWindow extends JFrame {
    private int width, height;
    private JLabel label;

    public TankBlastWindow(int width, int height) {
        setSize(width, height);
        setResizable(false);
        setLayout(new BorderLayout());
        label = new JLabel();
        add(label, BorderLayout.CENTER);
    }

    public void showFrame(BufferedImage frame) {
        if (frame.getWidth() != width || frame.getHeight() != height) throw new IllegalArgumentException();
        label.setIcon(new ImageIcon(frame));
        label.repaint();
    }
}