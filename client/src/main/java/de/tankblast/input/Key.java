package de.tankblast.input;

import java.awt.event.KeyEvent;

public enum Key {
    W(KeyEvent.VK_W),
    A(KeyEvent.VK_A),
    S(KeyEvent.VK_S),
    D(KeyEvent.VK_D),
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT),
    SPACE(KeyEvent.VK_SPACE),
    O(KeyEvent.VK_O),
    MOUSE_LEFT(-1);

    private final int keyCode;

    Key(int keyCode){
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}