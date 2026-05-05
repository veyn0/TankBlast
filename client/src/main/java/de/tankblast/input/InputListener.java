package de.tankblast.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {

    private InputManager inputManager;

    public InputListener(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.onKeyPress(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
       inputManager.onKeyRelease(e.getKeyCode());
    }
}
