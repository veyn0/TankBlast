package de.tankblast.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RenderPanel extends JPanel {
    private BufferedImage frame;

    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        BufferedImage img = frame;
        if(img!= null){
            g.drawImage(img, 0,0, getWidth(), getHeight(), null);
        }
    }


}
