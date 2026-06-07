package de.tankblast.input;

public class InputMapper {

    public static float getForwardKeyPercentage(InputContext inputContext){
        return getKeyPressedPercentage(inputContext, Key.W);
    }

    public static float getBackwardKeyPercentage(InputContext inputContext){
        return getKeyPressedPercentage(inputContext, Key.S);
    }

    public static float getRotateRightKeyPercentage(InputContext inputContext){
        return getKeyPressedPercentage(inputContext, Key.D);
    }

    public static float getRotateLeftKeyPercentage(InputContext inputContext){
        return getKeyPressedPercentage(inputContext, Key.A);
    }

    public static float getMovementPercentage(InputContext inputContext){
        return (float) Math.clamp((getForwardKeyPercentage(inputContext) - getBackwardKeyPercentage(inputContext)), -1, 1);
    }

    public static float getRotationPercentage(InputContext inputContext){
        return (float) Math.clamp((getRotateRightKeyPercentage(inputContext) - getRotateLeftKeyPercentage(inputContext)), -1, 1);
    }

    public static double getRotationDegrees(InputContext inputContext, double rotationSpeed){
        return (rotationSpeed * inputContext.getTimeSinceLastTickNanos() / 1_000_000_000.0) * getRotationPercentage(inputContext);
    }

    public static double getMovementDistance(InputContext inputContext, double movementSpeed){
        return (movementSpeed * inputContext.getTimeSinceLastTickNanos() / 1_000_000_000.0) * getMovementPercentage(inputContext);
    }

    public static boolean isShootPressed(InputContext inputContext){
        Long pressed = inputContext.getKeyStrokes().get(Key.SPACE.getKeyCode());
        return pressed != null && pressed > 0;
    }

    private static float getKeyPressedPercentage(InputContext inputContext, Key key){
        Long pressedNanos = inputContext.getKeyStrokes().get(key.getKeyCode());
        if (pressedNanos == null) return 0f;
        long tickNanos = inputContext.getTimeSinceLastTickNanos();
        if (tickNanos <= 0) return 0f;
        return (float) Math.clamp((double) pressedNanos / (double) tickNanos, 0, 1);
    }

}