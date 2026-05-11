package de.tankblast.input;

import java.util.Map;

public class InputMapper {

    private Map<Integer, Long> keyStrokes;

    public void setKeyStrokes(Map<Integer, Long> keyStrokes) {
        this.keyStrokes = keyStrokes;
    }

    private long timeSinceLastTickNanos;

    public float getForwardKeyPercentage(){

    }

    public float getBackwardKeyPercentage(){

    }

    public float getRotateRightKeyPercentage(){

    }

    public float getRotateLeftKeyPercentage(){

    }

    public float getMovement(){

    }

    public float getRotation(){

    }

    public float getRotationDegrees(double tickTimeNanos, double rotationSpeed){

    }

}
