package de.tankblast.input;

import java.awt.event.KeyEvent;

public enum Key {
    W(KeyEvent.VK_W),
    A(KeyEvent.VK_A),
    S(KeyEvent.VK_S),
    D(KeyEvent.VK_D),
    SPACE(KeyEvent.VK_SPACE);

    private final int keyCode;

    Key(int keyCode){
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}