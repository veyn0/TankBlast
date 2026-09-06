package de.tankblast.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMouseInputListener extends MouseAdapter {

    private final InputManager inputManager;

    public GameMouseInputListener(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            inputManager.onKeyPress(Key.MOUSE_LEFT.getKeyCode());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            inputManager.onKeyRelease(Key.MOUSE_LEFT.getKeyCode());
        }
    }
}
