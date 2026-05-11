package de.tankblast.input;

public enum Key {
    W(1),
    A(2),
    S(3),
    D(4),
    SPACE(5);

    private int keyCode;

    Key(int keyCode){
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
